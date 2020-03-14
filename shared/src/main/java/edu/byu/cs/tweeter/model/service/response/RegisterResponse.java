package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class RegisterResponse {
    private User registeredUser;

    public RegisterResponse(User registeredUser){
        this.registeredUser = registeredUser;
    }

    public User getUser(){return registeredUser;}

}
