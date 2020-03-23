package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IRegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.RegisterDAO;

public class RegisterServiceImpl implements IRegisterService {
    @Override
    public RegisterResponse register(RegisterRequest request) {
        RegisterDAO dao = new RegisterDAO();
        return dao.registerUser(request);
    }
}
