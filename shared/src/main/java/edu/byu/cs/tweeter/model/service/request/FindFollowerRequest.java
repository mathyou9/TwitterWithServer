package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FindFollowerRequest {

    private User currentUser;
    private User userBeingFollowed;

    private String auth;


    public FindFollowerRequest(){}
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

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setUserBeingFollowed(User userBeingFollowed) {
        this.userBeingFollowed = userBeingFollowed;
    }

    public void setAuth(String auth){
        this.auth = auth;
    }
    public String getAuth(){
        return auth;
    }

}
