package edu.byu.cs.tweeter.server.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryDAO {

    private static List<Tweet> story;

    public StoryResponse getStoryTweets(StoryRequest request){
        assert request.getLimit() >= 0;
        assert request.getUser() != null;

        if(story == null){
            story = initializeStory();
        }
        List<Tweet> responseTweets = new ArrayList<>(request.getLimit());
        boolean hasMorePages = true;
//        if(request.getLimit() > 0){
//            if(story != null){
//                int tweetIndex = getStoryStartingIndex(request.getLastTweet(), story);
//                for(int limitCounter = 0; tweetIndex < story.size() && limitCounter < request.getLimit(); tweetIndex++, limitCounter++){
//                    responseTweets.add(story.get(tweetIndex));
//                }
//                hasMorePages = tweetIndex < story.size();
//            }
//        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a ' - ' dd MMM YYYY");
        for(int i = 0; i < request.getLimit(); i++){
            Tweet tweet = new Tweet(request.getUser(), "message " + i, LocalDateTime.now().format(dateTimeFormatter).toString());
            responseTweets.add(tweet);
        }
        return new StoryResponse(responseTweets, hasMorePages);
    }

    private List<Tweet> initializeStory(){
        List<Tweet> story = new ArrayList<Tweet>();
        return story;
    }

    private int getStoryStartingIndex (Tweet lastTweet, List<Tweet> story){
        int storyIndex = 0;
        if (lastTweet != null){
            for(int i = 0; i < story.size(); i++){
                if(lastTweet.equals(story.get(i))){
                    storyIndex = i + 1;
                }
            }
        }
        return storyIndex;
    }
}
