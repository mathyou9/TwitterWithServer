package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IFindFollowerService;
import edu.byu.cs.tweeter.model.service.request.FindFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FindFollowerResponse;
import edu.byu.cs.tweeter.server.dao.FindFollowerDAO;

public class FindFollowerServiceImpl implements IFindFollowerService {
    @Override
    public FindFollowerResponse findFollower(FindFollowerRequest request) {
        FindFollowerDAO dao = new FindFollowerDAO();
        return dao.FindFollower(request);
    }
}
