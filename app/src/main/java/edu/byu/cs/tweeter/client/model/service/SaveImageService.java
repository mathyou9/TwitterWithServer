package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.SaveImageRequest;
import edu.byu.cs.tweeter.model.service.response.SaveImageResponse;

public class SaveImageService {
    private static SaveImageService instance;

    private static final String URL_PATH = "/saveimage";

    private final ServerFacade serverFacade;

    public static SaveImageService getInstance(){
        if(instance == null){
            instance = new SaveImageService();
        }
        return instance;
    }

    private SaveImageService(){
        serverFacade = new ServerFacade();
    }

    public SaveImageResponse saveImage(SaveImageRequest request) throws IOException{
        return serverFacade.saveImage(request, URL_PATH);
    }
}
