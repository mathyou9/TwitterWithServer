package edu.byu.cs.tweeter.client.model.service;


import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutService {

    private static final String URL_PATH = "/logout";

    private static LogoutService instance;
    private final ServerFacade serverFacade;

    public static LogoutService getInstance(){
        if(instance == null){
            instance = new LogoutService();
        }
        return instance;
    }

    private LogoutService() {serverFacade = new ServerFacade();}
    public LogoutResponse logout(LogoutRequest request) throws IOException {
        return serverFacade.logout(request, URL_PATH);
    }
}
