package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RemoveRequest;
import edu.byu.cs.tweeter.model.service.response.RemoveResponse;

public class RemoveFolloweeDAO {

    private static Map<User, List<User>> followeesByFollower;

    public RemoveResponse removeFollowee(RemoveRequest request){
        assert request.getUserToRemove() != null;
        assert request.getCurrentUser() != null;
//
//        if(followeesByFollower == null) {
//            followeesByFollower = initializeFollowees();
//        }
//        if(followeesByFollower.get(request.getCurrentUser()) == null){
//            return new RemoveResponse(false);
//        }
//        for(User user : followeesByFollower.get(request.getCurrentUser())){
//            if(user.getAlias().equals(request.getUserToRemove().getAlias())){
//                followeesByFollower.get(request.getCurrentUser()).remove(user);
//                return new RemoveResponse(true);
//            }
//        }
//        return new RemoveResponse(false);
        return new RemoveResponse(true);
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
