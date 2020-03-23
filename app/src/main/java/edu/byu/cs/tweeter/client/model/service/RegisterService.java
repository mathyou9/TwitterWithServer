package edu.byu.cs.tweeter.client.model.service;


import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterService {

    private static final String URL_PATH = "/register";

    private static RegisterService instance;

    private final ServerFacade serverFacade;

    public static RegisterService getInstance(){
        if(instance == null){
            instance = new RegisterService();
        }
        return instance;
    }

    private RegisterService() {
        serverFacade = new ServerFacade();
    }

    public RegisterResponse getRegisterResponse(RegisterRequest request) throws IOException {
        RegisterResponse response = serverFacade.register(request, URL_PATH);
        LoginService.getInstance().setCurrentUser(response.getRegisteredUser());
        return response;
    }

}
