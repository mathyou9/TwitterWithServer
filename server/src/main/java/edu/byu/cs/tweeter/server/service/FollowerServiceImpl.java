package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IFollowerService;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.server.dao.FollowersDAO;

public class FollowerServiceImpl implements IFollowerService {
    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        FollowersDAO dao = new FollowersDAO();
        return dao.getFollowers(request);
    }
}
