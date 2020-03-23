package edu.byu.cs.tweeter.server.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.ICreateTweetService;
import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;
import edu.byu.cs.tweeter.model.service.response.CreateTweetResponse;
import edu.byu.cs.tweeter.server.dao.CreateTweetDAO;

public class CreateTweetServiceImpl implements ICreateTweetService {
    @Override
    public CreateTweetResponse createTweet(CreateTweetRequest request) {
        CreateTweetDAO dao = new CreateTweetDAO();
        return dao.createTweet(request);
    }
}
