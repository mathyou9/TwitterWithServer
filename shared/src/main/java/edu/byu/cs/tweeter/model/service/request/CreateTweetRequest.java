package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class CreateTweetRequest {

    private User user;
    private String message;
    private String auth;

    public CreateTweetRequest(){}
    public CreateTweetRequest(User user, String message){
        this.user = user;
        this.message = message;
    }

    public User getUser(){return user;}
    public String getMessage(){return message;}

    public void setUser(User user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuth(String auth){
        this.auth = auth;
    }
    public String getAuth(){
        return auth;
    }
}
