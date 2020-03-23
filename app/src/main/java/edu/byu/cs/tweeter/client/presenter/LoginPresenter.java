package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginPresenter extends Presenter {

    private final View view;

    public interface View{

    }

    public LoginPresenter(View view) {this.view = view;}

    public LoginResponse getLoginUser (LoginRequest request) throws IOException {
        return LoginService.getInstance().login(request);
    }


}
