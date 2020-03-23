package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IFollowingService;
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
public class IFollowingServiceProxy implements IFollowingService {

    private static final String URL_PATH = "/getfollowing";

    private final ServerFacade serverFacade;

    private static IFollowingServiceProxy instance;

    public static IFollowingServiceProxy getInstance() {
        if(instance == null) {
            instance = new IFollowingServiceProxy();
        }

        return instance;
    }

    private IFollowingServiceProxy() {
        serverFacade = new ServerFacade();
    }


    public FollowingResponse getFollowees(FollowingRequest request) throws IOException {
        return serverFacade.getFollowees(request, URL_PATH);
    }

    public RemoveResponse removeFollowee(RemoveRequest request) throws IOException {
        return serverFacade.removeFollowee(request, "/removefollowee");
    }
    public FindFollowerResponse isFollowing(FindFollowerRequest request) throws IOException {
        return serverFacade.FindFollower(request, "/isfollowing");
    }
    public AddFollowResponse addFollowee(AddFollowRequest request) throws IOException {
        return serverFacade.AddFollowee(request, "/addfollowee");
    }
}
