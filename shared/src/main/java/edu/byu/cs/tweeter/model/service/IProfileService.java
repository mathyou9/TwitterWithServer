package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;

public interface IProfileService {
    ProfileResponse getProfile(ProfileRequest request) throws IOException;
}
