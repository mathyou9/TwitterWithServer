package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.request.AddFollowRequest;
import edu.byu.cs.tweeter.model.service.request.FindFollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RemoveRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowResponse;
import edu.byu.cs.tweeter.model.service.response.FindFollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.RemoveResponse;

/**
 * A remote-access proxy for accessing the 'following' service.
 */
public class FollowingServiceProxy {

    private static final String URL_PATH = "/getfollowing";

    private final ServerFacade serverFacade;

    private static FollowingServiceProxy instance;

    public static FollowingServiceProxy getInstance() {
        if(instance == null) {
            instance = new FollowingServiceProxy();
        }

        return instance;
    }

    private FollowingServiceProxy() {
        serverFacade = new ServerFacade();
    }


    public FollowingResponse getFollowees(FollowingRequest request) throws IOException {
        return serverFacade.getFollowees(request, URL_PATH);
    }

    public RemoveResponse removeFollowee(RemoveRequest request) throws IOException {
        return serverFacade.removeFollowee(request, "/removeFollowee");
    }
    public FindFollowerResponse isFollowing(FindFollowerRequest request) throws IOException {
        return serverFacade.FindFollower(request, "/isFollowing");
    }
    public AddFollowResponse addFollowee(AddFollowRequest request) throws IOException {
        return serverFacade.AddFollowee(request, "addFollowee");
    }
}
