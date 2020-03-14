package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.ProfileService;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;

public class ProfilePresenter extends Presenter {

    private final View view;

    public interface View{

    }
    public ProfilePresenter(View view){this.view = view;}

    public ProfileResponse getUserProfile(ProfileRequest request) throws IOException {
        return ProfileService.getInstance().getProfileResponse(request);
    }
    public User getUserProfile() {
        return ProfileService.getInstance().getSelectedUser();
    }

}
