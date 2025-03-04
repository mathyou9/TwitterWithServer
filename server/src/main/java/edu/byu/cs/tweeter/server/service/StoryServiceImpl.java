package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IStoryService;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.StoryDAO;

public class StoryServiceImpl implements IStoryService {
    @Override
    public StoryResponse getStory(StoryRequest request) {
        StoryDAO dao = new StoryDAO();
        return dao.getStoryTweets(request);
    }
}
