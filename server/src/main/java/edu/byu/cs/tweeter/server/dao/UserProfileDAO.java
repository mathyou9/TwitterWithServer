package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;

public class UserProfileDAO {

    private static Map<User, List<User>> followeesByFollower;

    private static List<User> followers;

    private static User selectedUser;

    public ProfileResponse getUserProfile(ProfileRequest request){
        assert request.getAlias() != null;
//        if(followeesByFollower == null) {
//            followeesByFollower = initializeFollowees();
//        }
//        if(followers == null){
//            followers = initializeFollowers();
//        }
//        selectedUser = null;
//        for(Map.Entry<User, List<User>> entry: followeesByFollower.entrySet()){
//            if(entry.getKey().getAlias().equals(request.getAlias())){
//                selectedUser = entry.getKey();
//                return new ProfileResponse(selectedUser);
//            }
//            for(User user : entry.getValue()){
//                if(user.getAlias().equals(request.getAlias())){
//                    selectedUser = user;
//                    return new ProfileResponse(selectedUser);
//                }
//            }
//        }
//        for(User user : followers){
//            if(user.getAlias().equals(request.getAlias())){
//                selectedUser = user;
//                return new ProfileResponse(selectedUser);
//            }
//        }

//        return new ProfileResponse(selectedUser);
        return new ProfileResponse(new User("Lookup", "User", "@LookupUser", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"));
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
