package edu.byu.cs.tweeter.model.service.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Tweet;

public class StoryResponse extends PagedResponse{
    private List<Tweet> story;

    public StoryResponse(String message, boolean hasMorePages){
        super(false, message, false);
    }
    public StoryResponse(List<Tweet> story, boolean hasMorePages){
        super(true, hasMorePages);
        this.story = story;
    }

    public List<Tweet> getStory() {return story;}
}
