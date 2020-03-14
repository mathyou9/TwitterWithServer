package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class RemoveRequest {
    User userToRemove;
    User currentUser;

    public RemoveRequest(User userToRemove, User currentUser){
        this.userToRemove = userToRemove;
        this.currentUser = currentUser;
    }

    public User getUserToRemove() {
        return userToRemove;
    }
    public User getCurrentUser(){
        return currentUser;
    }
}
