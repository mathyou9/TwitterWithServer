package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.ILogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.server.dao.LogoutDAO;

public class LogoutServiceImpl implements ILogoutService {
    @Override
    public LogoutResponse logout(LogoutRequest request) {
        LogoutDAO dao = new LogoutDAO();
        return dao.logoutUser(request);
    }
}
