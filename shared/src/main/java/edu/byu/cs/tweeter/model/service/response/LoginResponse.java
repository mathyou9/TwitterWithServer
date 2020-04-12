package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class LoginResponse {

    private User currentUser;

    private String message;

    private String auth;

    public LoginResponse(User currentUser, String auth){
        this.currentUser = currentUser;
        this.auth = auth;
    }

    public LoginResponse(String message){
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public User getCurrentUser(){
        return currentUser;
    }
    public String getAuth(){
        return auth;
    }
}
