package edu.byu.cs.tweeter.server.lambda.tweet;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;
import edu.byu.cs.tweeter.model.service.response.CreateTweetResponse;
import edu.byu.cs.tweeter.server.service.CreateTweetServiceImpl;

public class CreateTweetHandler implements RequestHandler<CreateTweetRequest, CreateTweetResponse> {
    @Override
    public CreateTweetResponse handleRequest(CreateTweetRequest createTweetRequest, Context context) {
        CreateTweetServiceImpl service = new CreateTweetServiceImpl();
        return service.createTweet(createTweetRequest);
    }
}
