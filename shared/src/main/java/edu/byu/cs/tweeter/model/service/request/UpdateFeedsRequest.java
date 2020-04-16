package edu.byu.cs.tweeter.model.service.request;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class UpdateFeedsRequest {
    List<User> users;
    String tweetID;

    public UpdateFeedsRequest(){

    }

    public String getTweetID() {
        return tweetID;
    }

    public void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
