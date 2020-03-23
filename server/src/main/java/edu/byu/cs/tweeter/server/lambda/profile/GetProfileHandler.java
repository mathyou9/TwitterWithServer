package edu.byu.cs.tweeter.server.lambda.profile;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;
import edu.byu.cs.tweeter.server.service.ProfileServiceImpl;

public class GetProfileHandler implements RequestHandler<ProfileRequest, ProfileResponse> {

    @Override
    public ProfileResponse handleRequest(ProfileRequest profileRequest, Context context) {
        ProfileServiceImpl service = new ProfileServiceImpl();
        return service.getProfile(profileRequest);
    }
}
