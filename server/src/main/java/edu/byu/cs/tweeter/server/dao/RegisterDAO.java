package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterDAO {

    private static Map<User, List<User>> followeesByFollower;

    private static List<User> followers;

    private static User currentUser;

    private String auth = "cNdY3D8Gv9ni97rwasRl";

    public RegisterResponse registerUser(RegisterRequest request){
        assert request.getEmail() != null;
        assert request.getFirstName() != null;
        assert request.getLastName() != null;
        assert request.getHandle() != null;
        assert request.getPassword() != null;

        if(followeesByFollower == null){
            followeesByFollower = initializeFollowees();
        }
        if(followers == null){
            followers = initializeFollowers();
        }

        currentUser = new User(request.getFirstName(), request.getLastName(), request.getHandle(), request.getImageUrl());


        followeesByFollower.put(currentUser, new ArrayList<User>());
        return new RegisterResponse(currentUser, auth);

    }

    private Map<User, List<User>> initializeFollowees() {

        Map<User, List<User>> followeesByFollower = new HashMap<>();

        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(50,
                0, 50, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);

        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for(Follow follow : follows) {
            List<User> followees = followeesByFollower.get(follow.getFollower());

            if(followees == null) {
                followees = new ArrayList<>();
                followeesByFollower.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }

        return followeesByFollower;
    }

    private List<User> initializeFollowers(){

        List<User> users = getFollowGenerator().generateFollowers(50);
        return users;

    }

    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }
}
