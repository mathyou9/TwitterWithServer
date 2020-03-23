package edu.byu.cs.tweeter.client.model.service;


import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;
import edu.byu.cs.tweeter.model.service.response.CreateTweetResponse;

public class CreateTweetService {

    private static final String URL_PATH = "/createtweet";

    private static CreateTweetService instance;

    private final ServerFacade serverFacade;

    public static CreateTweetService getInstance(){
        if(instance == null){
            instance = new CreateTweetService();
        }
        return instance;
    }

    private CreateTweetService() { serverFacade = new ServerFacade();}

    public CreateTweetResponse getTweetResponse(CreateTweetRequest request) throws IOException {
        return serverFacade.createTweetResponse(request, URL_PATH);
    }

}
