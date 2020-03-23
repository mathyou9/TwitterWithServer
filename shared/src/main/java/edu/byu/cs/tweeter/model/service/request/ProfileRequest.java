package edu.byu.cs.tweeter.model.service.request;

public class ProfileRequest {
    String alias;

    String auth;

    public ProfileRequest(){}
    public ProfileRequest(String alias){
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setAuth(String auth){
        this.auth = auth;
    }
    public String getAuth(){
        return auth;
    }
}
