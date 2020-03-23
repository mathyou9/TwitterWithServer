package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class RemoveRequest {
    User userToRemove;
    User currentUser;

    private String auth;

    public RemoveRequest(){}
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

    public void setUserToRemove(User userToRemove) {
        this.userToRemove = userToRemove;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setAuth(String auth){
        this.auth = auth;
    }
    public String getAuth(){
        return auth;
    }
}
