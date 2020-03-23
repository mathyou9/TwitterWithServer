package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FindFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FindFollowerResponse;

public class FindFollowerDAO {

    private static Map<User, List<User>> followeesByFollower;

    public FindFollowerResponse FindFollower(FindFollowerRequest request){
        assert request.getCurrentUser() != null;
        assert request.getUserBeingFollowed() != null;

        if(followeesByFollower == null) {
            followeesByFollower = initializeFollowees();
        }
        if(followeesByFollower.get(request.getCurrentUser()) == null){
            return new FindFollowerResponse(false);
        }
        followeesByFollower.get(request.getCurrentUser()).add(new User("Daffy", "Duck", ""));
        for(User user : followeesByFollower.get(request.getCurrentUser())){
            if(user.getAlias().equals(request.getUserBeingFollowed().getAlias())){
                return new FindFollowerResponse(true);
            }
        }


        return new FindFollowerResponse(false);
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
