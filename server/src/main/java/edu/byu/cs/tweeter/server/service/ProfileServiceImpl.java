package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IProfileService;
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;
import edu.byu.cs.tweeter.server.dao.UserProfileDAO;

public class ProfileServiceImpl implements IProfileService {
    @Override
    public ProfileResponse getProfile(ProfileRequest request) {
        UserProfileDAO dao = new UserProfileDAO();
        return dao.getUserProfile(request);
    }
}
