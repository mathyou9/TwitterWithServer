package edu.byu.cs.tweeter.model.service.request;

public class RegisterRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String handle;
    private String imageUrl;


    public RegisterRequest(){}
    public RegisterRequest(String email, String password, String firstName, String lastName, String handle, String imageUrl){
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.handle = handle;
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getHandle() {
        return handle;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
