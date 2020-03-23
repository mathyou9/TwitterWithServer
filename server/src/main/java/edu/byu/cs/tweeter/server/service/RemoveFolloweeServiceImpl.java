package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IRemoveFollowee;
import edu.byu.cs.tweeter.model.service.request.RemoveRequest;
import edu.byu.cs.tweeter.model.service.response.RemoveResponse;
import edu.byu.cs.tweeter.server.dao.RemoveFolloweeDAO;

public class RemoveFolloweeServiceImpl implements IRemoveFollowee {
    @Override
    public RemoveResponse removeFollowee(RemoveRequest request) {
        RemoveFolloweeDAO dao = new RemoveFolloweeDAO();
        return dao.removeFollowee(request);
    }
}
