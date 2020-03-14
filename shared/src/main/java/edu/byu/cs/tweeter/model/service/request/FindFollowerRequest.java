package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FindFollowerRequest {

    private User currentUser;
    private User userBeingFollowed;

    public FindFollowerRequest(User currentUser, User userBeingFollowed){
        this.currentUser = currentUser;
        this.userBeingFollowed = userBeingFollowed;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getUserBeingFollowed() {
        return userBeingFollowed;
    }
}
