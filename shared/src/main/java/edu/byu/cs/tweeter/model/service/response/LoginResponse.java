package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class LoginResponse {

    private User currentUser;

    private String auth;

    public LoginResponse(User currentUser, String auth){
        this.currentUser = currentUser;
        this.auth = auth;
    }

    public User getCurrentUser(){
        return currentUser;
    }
    public String getAuth(){
        return auth;
    }
}
