package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class LoginResponse {

    private User currentUser;

    public LoginResponse(User currentUser){
        this.currentUser = currentUser;
    }

    public User getCurrentUser(){
        return currentUser;
    }
}
