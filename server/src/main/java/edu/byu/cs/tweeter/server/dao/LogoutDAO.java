package edu.byu.cs.tweeter.server.dao;

import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutDAO {

    private static Map<User, List<User>> followeesByFollower;

    private static List<User> followers;

    private static List<Tweet> story;

    private static Map<User, List<Tweet>> tweetsByUser;

    private static User currentUser;

    private static User selectedUser;

    public LogoutResponse logoutUser(LogoutRequest request){
        assert request.getUser() != null;

        followeesByFollower = null;
        followers = null;
        story = null;
        tweetsByUser = null;
        currentUser = null;
        selectedUser = null;

        return new LogoutResponse(true);
    }
}
