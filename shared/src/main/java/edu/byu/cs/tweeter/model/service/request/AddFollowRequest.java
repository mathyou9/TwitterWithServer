package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class AddFollowRequest {
    private User currentUser;
    private User userToAdd;

    public AddFollowRequest(User currentUser, User userToAdd){
        this.currentUser = currentUser;
        this.userToAdd = userToAdd;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getUserToAdd() {
        return userToAdd;
    }
}
