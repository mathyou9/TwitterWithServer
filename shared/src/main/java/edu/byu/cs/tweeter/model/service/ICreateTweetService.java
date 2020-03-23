package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;
import edu.byu.cs.tweeter.model.service.response.CreateTweetResponse;

public interface ICreateTweetService {
    CreateTweetResponse createTweet(CreateTweetRequest request) throws IOException;
}
