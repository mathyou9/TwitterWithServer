package edu.byu.cs.tweeter.server.lambda.login;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.server.service.LogoutServiceImpl;

public class LogoutHandler implements RequestHandler<LogoutRequest, LogoutResponse> {
    @Override
    public LogoutResponse handleRequest(LogoutRequest logoutRequest, Context context) {
        LogoutServiceImpl service = new LogoutServiceImpl();
        return service.logout(logoutRequest);
    }
}
