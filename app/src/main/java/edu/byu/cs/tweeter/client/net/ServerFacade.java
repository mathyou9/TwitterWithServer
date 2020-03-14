package edu.byu.cs.tweeter.client.net;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.AddFollowRequest;
import edu.byu.cs.tweeter.model.service.request.FindFollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.request.RemoveRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowResponse;
import edu.byu.cs.tweeter.model.service.response.FindFollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;
import edu.byu.cs.tweeter.model.service.response.RemoveResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    // TODO: Set this the the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "https://mvm2yzu10i.execute-api.us-west-2.amazonaws.com/Production";

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);
    }
    //need to implement server side
    public FollowersResponse getFollowers (FollowersRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, FollowersResponse.class);
    }

    public ProfileResponse getUserProfile (ProfileRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, ProfileResponse.class);
    }

    public RemoveResponse removeFollowee (RemoveRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, RemoveResponse.class);
    }

    public FindFollowerResponse FindFollower (FindFollowerRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, FindFollowerResponse.class);
    }

    public AddFollowResponse AddFollowee (AddFollowRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, AddFollowResponse.class);
    }


}
