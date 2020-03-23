package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryService {

    private static final String URL_PATH = "/getstory";

    private static StoryService instance;

    private final ServerFacade serverFacade;

    public static StoryService getInstance() {
        if(instance == null){
            instance = new StoryService();
        }
        return instance;
    }

    private StoryService(){ serverFacade = new ServerFacade(); }

    public StoryResponse getTweets(StoryRequest request) throws IOException {
        return serverFacade.getStoryTweets(request, URL_PATH);
    }

}
