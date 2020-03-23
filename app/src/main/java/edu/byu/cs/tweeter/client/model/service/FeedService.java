package edu.byu.cs.tweeter.client.model.service;


import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedService {

    private static final String URL_PATH = "/getfeed";

    private static FeedService instance;

    private final ServerFacade serverFacade;

    public static FeedService getInstance(){
        if(instance == null){
            instance = new FeedService();
        }

        return instance;
    }

    private FeedService() {
        serverFacade = new ServerFacade();
    }

    public FeedResponse getTweets(FeedRequest request) throws IOException {
        return serverFacade.getFeed(request, URL_PATH);
    }

}
