package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;

public class FollowersDAO {

    private static List<User> followers;

    public FollowersResponse getFollowers(FollowersRequest request){
        assert request.getLimit() >= 0;
        assert request.getFollower() != null;

        if(followers == null){
            followers = initializeFollowers();
        }
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0){
            if(followers != null){
                int followersIndex = getFollowersStartingIndex(request.getLastFollower(), followers);
                for(int limitCounter = 0; followersIndex < followers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++){
                    responseFollowers.add(followers.get(followersIndex));
                }
                hasMorePages = followersIndex < followers.size();
            }
        }
        return new FollowersResponse(responseFollowers, hasMorePages);
    }

    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers){
        int followeesIndex = 0;
        if(lastFollower != null){
            for(int i = 0; i < allFollowers.size(); i++){
                if(lastFollower.equals(allFollowers.get(i))){
                    followeesIndex = i + 1;
                }
            }
        }
        return followeesIndex;
    }

    private List<User> initializeFollowers(){
        List<User> users = getFollowGenerator().generateFollowers(50);
        return users;

    }

    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }

}
