package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IFeedService;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;

public class FeedServiceImpl implements IFeedService {
    @Override
    public FeedResponse getFeed(FeedRequest request) {
        FeedDAO dao = new FeedDAO();
        return dao.getFeed(request);
    }
}
