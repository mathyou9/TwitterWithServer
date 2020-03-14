package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class CreateTweetRequest {

    private final User user;
    private final String message;

    public CreateTweetRequest(User user, String message){
        this.user = user;
        this.message = message;
    }

    public User getUser(){return user;}
    public String getMessage(){return message;}

}
