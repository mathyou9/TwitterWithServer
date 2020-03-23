package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.ILoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.LoginDAO;

public class LoginServiceImpl implements ILoginService {
    @Override
    public LoginResponse login(LoginRequest request) {
        LoginDAO dao = new LoginDAO();
        return dao.loginUser(request);
    }
}
