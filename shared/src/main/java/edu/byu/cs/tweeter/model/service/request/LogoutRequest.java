package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class LogoutRequest {
    private User user;

    private String auth;

    public LogoutRequest(){}
    public LogoutRequest(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuth(String auth){
        this.auth = auth;
    }
    public String getAuth(){
        return auth;
    }
}
