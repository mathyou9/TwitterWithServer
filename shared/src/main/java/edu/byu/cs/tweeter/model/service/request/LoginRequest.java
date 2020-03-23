package edu.byu.cs.tweeter.model.service.request;

public class LoginRequest {

    private String email;
    private String password;

    private String auth;

    private LoginRequest(){}

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
    public void setAuth(String auth){
        this.auth = auth;
    }
    public String getAuth(){
        return auth;
    }
}
