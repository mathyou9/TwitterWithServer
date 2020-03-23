package edu.byu.cs.tweeter.server.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedDAO {

    private static Map<User, List<User>> followeesByFollower;

    private static Map<User, List<Tweet>> tweetsByUser;

    public FeedResponse getFeed(FeedRequest request){
        assert request.getLimit() >= 0;
        assert request.getUser() != null;

        if(tweetsByUser == null){
            tweetsByUser = initializeFeed();
        }
        if(followeesByFollower == null){
            followeesByFollower = initializeFollowees();
        }
        List<Tweet> responseTweets = new ArrayList<>(request.getLimit());
        List<User> allFollowing = followeesByFollower.get(request.getUser());
        if(allFollowing == null){
            return new FeedResponse(responseTweets, false);
        }
        List<Tweet> allTweets = new ArrayList<Tweet>();
        for(User user : allFollowing){
            if(tweetsByUser.get(user) != null){
                allTweets.addAll(tweetsByUser.get(user));
            }
        }


        boolean hasMorePages = false;

        if(request.getLimit() > 0){
            if(allTweets != null){
                int tweetsIndex = getFeedTweetsStartingIndex(request.getLastTweet(), allTweets);

                for(int limitCounter = 0; tweetsIndex < allTweets.size() && limitCounter < request.getLimit(); tweetsIndex++, limitCounter++){
                    responseTweets.add(allTweets.get(tweetsIndex));
                }
                hasMorePages = tweetsIndex < allTweets.size();
            }
        }
        return new FeedResponse(responseTweets, hasMorePages);
    }

    private Map<User, List<Tweet>> initializeFeed(){
        Map<User, List<Tweet>> tweetsByUser = new HashMap<>();
        if(followeesByFollower == null){
            followeesByFollower = initializeFollowees();
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("hh:mm a ' - ' dd MMM YYYY");
        for (Map.Entry<User, List<User>> entry : followeesByFollower.entrySet()){
            tweetsByUser.put(entry.getKey(), new ArrayList<Tweet>());
            for(int i = 0; i < 3; i++){
                tweetsByUser.get(entry.getKey()).add(new Tweet(entry.getKey(), "This is tweet " + i, LocalDateTime.now().format(dateFormatter).toString()));
            }
        }
        List<Tweet> feed = new ArrayList<Tweet>();

        return tweetsByUser;
    }

    private Map<User, List<User>> initializeFollowees() {

        Map<User, List<User>> followeesByFollower = new HashMap<>();

        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(50,
                0, 50, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);

        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for(Follow follow : follows) {
            List<User> followees = followeesByFollower.get(follow.getFollower());

            if(followees == null) {
                followees = new ArrayList<>();
                followeesByFollower.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }

        return followeesByFollower;
    }

    private int getFeedTweetsStartingIndex (Tweet lastTweet, List<Tweet> allTweets){

        int tweetIndex = 0;

        if(lastTweet != null){
            for(int i = 0; i < allTweets.size(); i++){
                if(lastTweet.equals(allTweets.get(i))){
                    tweetIndex = i + 1;
                }
            }
        }
        return tweetIndex;
    }

    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }
}
