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
import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;
import edu.byu.cs.tweeter.model.service.response.CreateTweetResponse;

public class CreateTweetDAO {

    private static List<Tweet> story;

    private static Map<User, List<User>> followeesByFollower;

    private static Map<User, List<Tweet>> tweetsByUser;

    DateTimeFormatter dateFormatter;

    public CreateTweetResponse createTweet (CreateTweetRequest request){
        assert request.getUser() != null;
        dateFormatter = DateTimeFormatter.ofPattern("hh:mm a ' - ' dd MMM YYYY");
        System.out.println(request.getMessage());
        System.out.println(request.getUser());
        if(story == null){
            story = new ArrayList<Tweet>();
        }
        if(tweetsByUser == null){
            tweetsByUser = initializeFeed();
        }

        if(tweetsByUser.get(request.getUser()) != null){
            Tweet tweet = new Tweet(request.getUser(), request.getMessage(), LocalDateTime.now().format(dateFormatter).toString());
            story.add(tweet);
            tweetsByUser.get(request.getUser()).add(tweet);
            return new CreateTweetResponse(request.getUser(), request.getMessage());
        } else {
            tweetsByUser.put(request.getUser(), new ArrayList<Tweet>());
            Tweet tweet = new Tweet(request.getUser(), request.getMessage(), LocalDateTime.now().format(dateFormatter).toString());
            story.add(tweet);
            tweetsByUser.get(request.getUser()).add(tweet);
            return new CreateTweetResponse(request.getUser(), request.getMessage());
        }
    }

    private Map<User, List<Tweet>> initializeFeed(){
        Map<User, List<Tweet>> tweetsByUser = new HashMap<>();
        if(followeesByFollower == null){
            followeesByFollower = initializeFollowees();
        }
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

    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }
}
