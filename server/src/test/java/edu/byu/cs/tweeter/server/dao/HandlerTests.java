package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.AddFollowRequest;
import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FindFollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
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
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.RemoveResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.lambda.follower.FindFollowerHandler;
import edu.byu.cs.tweeter.server.lambda.follower.GetFollowerHandler;
import edu.byu.cs.tweeter.server.lambda.following.AddFolloweeHandler;
import edu.byu.cs.tweeter.server.lambda.following.GetFollowingHandler;
import edu.byu.cs.tweeter.server.lambda.following.RemoveFolloweeHandler;
import edu.byu.cs.tweeter.server.lambda.login.LoginHandler;
import edu.byu.cs.tweeter.server.lambda.login.RegisterHandler;
import edu.byu.cs.tweeter.server.lambda.profile.GetProfileHandler;
import edu.byu.cs.tweeter.server.lambda.tweet.CreateTweetHandler;
import edu.byu.cs.tweeter.server.lambda.tweet.GetFeedHandler;
import edu.byu.cs.tweeter.server.lambda.tweet.GetStoryHandler;

class HandlerTests {

    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", "");
    private final User mainUser = new User("Test", "User", "@TestUser", "");
    private final User doesntExist = new User("asdf", "asdf", "@asdfjkl;", "");

    @BeforeEach
    void setup() {

    }


    //FIND FOLLOWER TESTS
    @Test
    void testFindFollowerHandlerNotThere(){
        FindFollowerRequest request = new FindFollowerRequest(user1, user2);
        FindFollowerHandler handler = new FindFollowerHandler();
        FindFollowerResponse response = handler.handleRequest(request, null);
        Assertions.assertFalse(response.isFound());
    }
    @Test
    void testFindFollowerHandlerIsThere(){
        FindFollowerRequest request = new FindFollowerRequest(mainUser, user1);
        FindFollowerHandler handler = new FindFollowerHandler();
        FindFollowerResponse response = handler.handleRequest(request, null);
        Assertions.assertTrue(response.isFound());
    }

    //GET FOLLOWER TESTS
    @Test
    void testGetFollowerHandler(){
        FollowersRequest request = new FollowersRequest(mainUser, 10, null);
        GetFollowerHandler handler = new GetFollowerHandler();
        FollowersResponse response = handler.handleRequest(request, null);
        Assertions.assertNotNull(response.getFollowers());
        Assertions.assertEquals(10, response.getFollowers().size());
        Assertions.assertTrue(response.getHasMorePages());
    }

    //GET FEED TESTS
    @Test
    void testGetFeedHandler(){
        FeedRequest request = new FeedRequest(mainUser, 10, null);
        GetFeedHandler handler = new GetFeedHandler();
        FeedResponse response = handler.handleRequest(request, null);
        Assertions.assertNotNull(response.getTweets());
        Assertions.assertEquals(10, response.getTweets().size());
        Assertions.assertTrue(response.getHasMorePages());
    }

    @Test
    void testGetFeedHandlerNoUser(){
        FeedRequest request = new FeedRequest(doesntExist, 10, null);
        GetFeedHandler handler = new GetFeedHandler();
        FeedResponse response = handler.handleRequest(request, null);
        Assertions.assertNotNull(response.getTweets());
        Assertions.assertEquals(0, response.getTweets().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    //GET STORY TESTS
    @Test
    void testGetStoryHandler(){
        StoryRequest request = new StoryRequest(mainUser, 10, null);
        GetStoryHandler handler = new GetStoryHandler();
        StoryResponse response = handler.handleRequest(request, null);
        Assertions.assertNotNull(response.getStory());
        Assertions.assertEquals(10, response.getStory().size());
        Assertions.assertTrue(response.getHasMorePages());
    }
    @Test
    void testGetStoryHandler2Page(){
        StoryRequest request = new StoryRequest(mainUser, 10, null);
        GetStoryHandler handler = new GetStoryHandler();
        StoryResponse response = handler.handleRequest(request, null);
        response = handler.handleRequest(new StoryRequest(mainUser, 10, response.getStory().get(9)), null);
        Assertions.assertNotNull(response.getStory());
        Assertions.assertEquals(10, response.getStory().size());
        Assertions.assertTrue(response.getHasMorePages());
    }

    //ADD FOLLOWEE TESTS
    @Test
    void AddFolloweeHandler(){
        AddFollowRequest request = new AddFollowRequest(mainUser, user1);
        AddFolloweeHandler handler = new AddFolloweeHandler();
        AddFollowResponse response = handler.handleRequest(request, null);
        Assertions.assertTrue(response.getDidAdd());
    }

    @Test
    void AddFolloweeHandlerTwice(){
        AddFollowRequest request = new AddFollowRequest(mainUser, user1);
        AddFolloweeHandler handler = new AddFolloweeHandler();
        AddFollowResponse response = handler.handleRequest(request, null);
        response = handler.handleRequest(request, null);
        Assertions.assertTrue(response.getDidAdd());
    }

    //GET FOLLOWING TESTS
    @Test
    void testGetFollowingHandler(){
        FollowingRequest request = new FollowingRequest(mainUser, 10, null);
        GetFollowingHandler handler = new GetFollowingHandler();
        FollowingResponse response = handler.handleRequest(request, null);
        Assertions.assertNotNull(response.getFollowees());
        Assertions.assertEquals(10, response.getFollowees().size());
        Assertions.assertTrue(response.getHasMorePages());
    }
    @Test
    void testGetFollowingHandlerUserDoesntExist(){
        FollowingRequest request = new FollowingRequest(doesntExist, 10, null);
        GetFollowingHandler handler = new GetFollowingHandler();
        FollowingResponse response = handler.handleRequest(request, null);
        Assertions.assertNotNull(response.getFollowees());
        Assertions.assertEquals(0, response.getFollowees().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    //REMOVE FOLLOWEE TESTS
    @Test
    void testRemoveFolloweeHandler(){
        RemoveRequest request = new RemoveRequest(user1, mainUser);
        RemoveFolloweeHandler handler = new RemoveFolloweeHandler();
        RemoveResponse response = handler.handleRequest(request, null);
        Assertions.assertTrue(response.getDidRemove());
    }

    @Test
    void testRemoveFolloweeHandlerDoesntExist(){
        RemoveRequest request = new RemoveRequest(user3, doesntExist);
        RemoveFolloweeHandler handler = new RemoveFolloweeHandler();
        RemoveResponse response = handler.handleRequest(request, null);
        Assertions.assertTrue(response.getDidRemove());
    }

    //LOGIN TESTS
    @Test
    void testLogin(){
        LoginRequest request = new LoginRequest("email", "password");
        LoginHandler handler = new LoginHandler();
        LoginResponse response = handler.handleRequest(request, null);
        Assertions.assertEquals("email", response.getCurrentUser().getAlias());
    }

    //REGISTER TESTS
    @Test
    void testRegister(){
        RegisterRequest request = new RegisterRequest("email", "password", "firstName", "lastName", "@Handle", "");
        RegisterHandler handler = new RegisterHandler();
        RegisterResponse response = handler.handleRequest(request, null);
        Assertions.assertEquals("firstName", response.getRegisteredUser().getFirstName());
        Assertions.assertEquals("lastName", response.getRegisteredUser().getLastName());
        Assertions.assertEquals("@Handle", response.getRegisteredUser().getAlias());
    }

    //GET PROFILE TESTS
    @Test
    void testGetProfile(){
        ProfileRequest request = new ProfileRequest("@LookupUser");
        GetProfileHandler handler = new GetProfileHandler();
        ProfileResponse response = handler.handleRequest(request, null);
        Assertions.assertEquals("@LookupUser", response.getSelectedUser().getAlias());
    }

    @Test
    void testGetProfileAnyOtherAlias(){
        ProfileRequest request = new ProfileRequest("@asdfasdf");
        GetProfileHandler handler = new GetProfileHandler();
        ProfileResponse response = handler.handleRequest(request, null);
        Assertions.assertEquals("@LookupUser", response.getSelectedUser().getAlias());
    }

    //CREATE TWEET TESTS
    @Test
    void testCreateTweetHandler(){
        CreateTweetRequest request = new CreateTweetRequest(mainUser, "hi");
        CreateTweetHandler handler = new CreateTweetHandler();
        CreateTweetResponse response = handler.handleRequest(request, null);
        Assertions.assertEquals(mainUser, response.getUser());
        Assertions.assertEquals("hi", response.getMessage());
    }

    @Test
    void testCreateTweetHandlerNoUser(){
        CreateTweetRequest request = new CreateTweetRequest(doesntExist, "hi");
        CreateTweetHandler handler = new CreateTweetHandler();
        CreateTweetResponse response = handler.handleRequest(request, null);
        Assertions.assertEquals(doesntExist, response.getUser());
        Assertions.assertEquals("hi", response.getMessage());
    }
}
