package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowersRequest {

    private final User follower;
    private final int limit;
    private final User lastFollower;

    public FollowersRequest(User follower, int limit, User lastFollower){
        this.follower = follower;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    public User getFollower() {
        return follower;
    }

    public int getLimit() {
        return limit;
    }

    public User getLastFollower(){
        return lastFollower;
    }

}
