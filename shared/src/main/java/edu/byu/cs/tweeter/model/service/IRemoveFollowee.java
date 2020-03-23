package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.RemoveRequest;
import edu.byu.cs.tweeter.model.service.response.RemoveResponse;

public interface IRemoveFollowee {
    RemoveResponse removeFollowee(RemoveRequest request) throws IOException;
}
