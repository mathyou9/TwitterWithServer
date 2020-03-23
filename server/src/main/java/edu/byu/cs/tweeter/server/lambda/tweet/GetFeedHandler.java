package edu.byu.cs.tweeter.server.lambda.tweet;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.service.FeedServiceImpl;

public class GetFeedHandler implements RequestHandler<FeedRequest, FeedResponse> {
    @Override
    public FeedResponse handleRequest(FeedRequest feedRequest, Context context) {
        FeedServiceImpl service = new FeedServiceImpl();
        return service.getFeed(feedRequest);
    }
}
