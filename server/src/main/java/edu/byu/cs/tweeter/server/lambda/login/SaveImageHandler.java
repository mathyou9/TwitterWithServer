package edu.byu.cs.tweeter.server.lambda.login;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.SaveImageRequest;
import edu.byu.cs.tweeter.model.service.response.SaveImageResponse;
import edu.byu.cs.tweeter.server.service.SaveImageServiceImpl;

public class SaveImageHandler implements RequestHandler<SaveImageRequest, SaveImageResponse> {
    @Override
    public SaveImageResponse handleRequest(SaveImageRequest request, Context context) {
        SaveImageServiceImpl service = new SaveImageServiceImpl();
        try{
            return service.saveImage(request);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
