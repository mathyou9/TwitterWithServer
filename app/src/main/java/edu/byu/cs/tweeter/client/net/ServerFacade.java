package edu.byu.cs.tweeter.client.net;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.AddFollowRequest;
import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FindFollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.RemoveRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowResponse;
import edu.byu.cs.tweeter.model.service.response.CreateTweetResponse;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.FindFollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.RemoveResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

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

    private String auth = "cNdY3D8Gv9ni97rwasRl";

    public FollowingResponse getFollowees(FollowingRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        request.setAuth(auth);
        return clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);
    }

    public FollowersResponse getFollowers (FollowersRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        request.setAuth(auth);
        return clientCommunicator.doPost(urlPath, request, null, FollowersResponse.class);
    }

    public ProfileResponse getUserProfile (ProfileRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        request.setAuth(auth);
        return clientCommunicator.doPost(urlPath, request, null, ProfileResponse.class);
    }

    //TODO server
    public RemoveResponse removeFollowee (RemoveRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        request.setAuth(auth);
        return clientCommunicator.doPost(urlPath, request, null, RemoveResponse.class);
    }

    public FindFollowerResponse FindFollower (FindFollowerRequest request, String urlPath) throws IOException {
        return new FindFollowerResponse(true);
    }

    public AddFollowResponse AddFollowee (AddFollowRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        request.setAuth(auth);
        return clientCommunicator.doPost(urlPath, request, null, AddFollowResponse.class);
    }


    public CreateTweetResponse createTweetResponse(CreateTweetRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        request.setAuth(auth);
        return clientCommunicator.doPost(urlPath, request, null, CreateTweetResponse.class);
    }

    public FeedResponse getFeed(FeedRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        request.setAuth(auth);
        return clientCommunicator.doPost(urlPath, request, null, FeedResponse.class);
    }

    public LogoutResponse logout(LogoutRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        request.setAuth(auth);
        return clientCommunicator.doPost(urlPath, request, null, LogoutResponse.class);
    }

    public RegisterResponse register(RegisterRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        return clientCommunicator.doPost(urlPath, request, null, RegisterResponse.class);
    }

    public StoryResponse getStoryTweets(StoryRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        request.setAuth(auth);
        return clientCommunicator.doPost(urlPath, request, null, StoryResponse.class);
    }

    public LoginResponse login(LoginRequest request, String urlPath) throws IOException {
        ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);
        LoginResponse response = clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);
        this.auth = response.getAuth();
        return response;
    }


}
