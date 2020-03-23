package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class RegisterResponse {
    private User registeredUser;
    private String auth;

    public RegisterResponse(User registeredUser, String auth){
        this.registeredUser = registeredUser;
        this.auth = auth;
    }

    public User getRegisteredUser(){return registeredUser;}

    public void setAuth(String auth){
        this.auth = auth;
    }
    public String getAuth(){
        return auth;
    }

}
