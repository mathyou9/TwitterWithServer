package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FindFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FindFollowerResponse;

public interface IFindFollowerService {
    FindFollowerResponse findFollower(FindFollowerRequest request) throws IOException;
}
