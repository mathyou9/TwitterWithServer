package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginDAO {
    private static User currentUser;
    private String auth = "cNdY3D8Gv9ni97rwasRl";
    public LoginResponse loginUser(LoginRequest request){
        assert request.getEmail() != null;
        assert request.getPassword() != null;
        if(currentUser == null){
//            currentUser = new User("Test", "User", "@TestUser", "content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F27/ORIGINAL/NONE/1789097202");
            currentUser = new User("Test", "User", request.getEmail(), "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            return new LoginResponse(currentUser, auth);
        }
        if(request.getEmail() == currentUser.getAlias() && request.getPassword() == "password"){
            return new LoginResponse(currentUser, auth);
        }
        currentUser.setAlias(request.getEmail());
        return new LoginResponse(currentUser, auth);

    }
}
