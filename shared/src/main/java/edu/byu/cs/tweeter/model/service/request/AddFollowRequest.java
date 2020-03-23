package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class AddFollowRequest {
    private User currentUser;
    private User userToAdd;
    private String auth;

    public AddFollowRequest(){}
    public AddFollowRequest(User currentUser, User userToAdd){
        this.currentUser = currentUser;
        this.userToAdd = userToAdd;
        this.auth = auth;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getUserToAdd() {
        return userToAdd;
    }

    public void setCurrentUser(User currentUser){
        this.currentUser = currentUser;
    }

    public void setUserToAdd(User userToAdd){
        this.userToAdd = userToAdd;
    }

    public void setAuth(String auth){
        this.auth = auth;
    }
    public String getAuth(){
        return auth;
    }
}
