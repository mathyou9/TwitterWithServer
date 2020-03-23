package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowersRequest {

    private User follower;
    private int limit;
    private User lastFollower;
    private String auth;

    private FollowersRequest(){}
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

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastFollower(User lastFollower) {
        this.lastFollower = lastFollower;
    }

    public void setAuth(String auth){
        this.auth = auth;
    }
    public String getAuth(){
        return auth;
    }
}
