package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IAddFolloweeService;
import edu.byu.cs.tweeter.model.service.request.AddFollowRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowResponse;
import edu.byu.cs.tweeter.server.dao.AddFolloweeDAO;

public class AddFolloweeServiceImpl implements IAddFolloweeService {
    @Override
    public AddFollowResponse addFollowee(AddFollowRequest request) {
        AddFolloweeDAO dao = new AddFolloweeDAO();
        return dao.AddFollowee(request);
    }
}
