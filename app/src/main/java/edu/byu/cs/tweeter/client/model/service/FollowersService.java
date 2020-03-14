package edu.byu.cs.tweeter.client.model.service;


import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;

public class FollowersService {

    private static final String URL_PATH = "/getFollowers";

    private static FollowersService instance;

    private final ServerFacade serverFacade;

    public static FollowersService getInstance(){
        if(instance == null){
            instance = new FollowersService();
        }
        return instance;
    }

    private FollowersService() { serverFacade = new ServerFacade();}

    public FollowersResponse getFollowers(FollowersRequest request) throws IOException {
        return serverFacade.getFollowers(request, URL_PATH);
//        return null;
    }
}
