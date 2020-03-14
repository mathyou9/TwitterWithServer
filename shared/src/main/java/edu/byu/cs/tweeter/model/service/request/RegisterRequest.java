package edu.byu.cs.tweeter.model.service.request;

public class RegisterRequest {

    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String handle;
    private final String imageUrl;

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

}
