package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.AddFollowRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowResponse;

public class AddFolloweeDAO {

    private static Map<User, List<User>> followeesByFollower;
    public AddFollowResponse AddFollowee (AddFollowRequest request) throws Exception {
        assert request.getCurrentUser() != null;
        assert request.getUserToAdd() != null;
        if(followeesByFollower == null) {
            followeesByFollower = initializeFollowees();
        }
        if(followeesByFollower.get(request.getCurrentUser()) == null){

            return new AddFollowResponse(false);
//            throw new Exception("Bad Request: No user exists");
        }
        try{
            followeesByFollower.get(request.getCurrentUser()).add(request.getUserToAdd());
        } catch (Exception e){
//            return new AddFollowResponse(false);
            throw new Exception("Bad Request: No user exists");
        }

        int index = followeesByFollower.get(request.getCurrentUser()).indexOf(request.getUserToAdd());
        if(followeesByFollower.get(request.getCurrentUser()).get(index) != null){
            return new AddFollowResponse(true);
        } else {
            throw new Exception("Bad Request: No user exists");
        }
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

    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }
}
