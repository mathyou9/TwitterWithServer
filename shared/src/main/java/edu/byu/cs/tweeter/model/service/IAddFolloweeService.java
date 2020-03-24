package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.AddFollowRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowResponse;

public interface IAddFolloweeService {
    AddFollowResponse addFollowee(AddFollowRequest request) throws Exception;
}
