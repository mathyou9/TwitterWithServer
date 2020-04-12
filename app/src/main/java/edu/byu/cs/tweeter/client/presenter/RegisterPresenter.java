package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.RegisterService;
import edu.byu.cs.tweeter.client.model.service.SaveImageService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.SaveImageRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.SaveImageResponse;

public class RegisterPresenter extends Presenter {

    private final View view;

    public String email;
    public String password;

    public interface View{

    }
    public RegisterPresenter(View view){this.view = view;}

    public RegisterResponse getRegisteredUser (RegisterRequest request) throws IOException {
        return RegisterService.getInstance().getRegisterResponse(request);
    }

    public SaveImageResponse saveImage(SaveImageRequest request)throws IOException{
        return SaveImageService.getInstance().saveImage(request);
    }
}
