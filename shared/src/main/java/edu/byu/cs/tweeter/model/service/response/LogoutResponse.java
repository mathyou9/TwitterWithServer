package edu.byu.cs.tweeter.model.service.response;

public class LogoutResponse {
    public boolean didLogout;
    public LogoutResponse(boolean didLogout){
        this.didLogout = didLogout;
    }

    public boolean isDidLogout() {
        return didLogout;
    }
}
