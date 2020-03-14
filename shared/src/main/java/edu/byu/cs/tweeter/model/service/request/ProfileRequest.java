package edu.byu.cs.tweeter.model.service.request;

public class ProfileRequest {
    String alias;
    public ProfileRequest(String alias){
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
