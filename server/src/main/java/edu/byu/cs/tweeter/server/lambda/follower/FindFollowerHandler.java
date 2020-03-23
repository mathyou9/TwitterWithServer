package edu.byu.cs.tweeter.server.lambda.follower;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FindFollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FindFollowerResponse;
import edu.byu.cs.tweeter.server.service.FindFollowerServiceImpl;

public class FindFollowerHandler implements RequestHandler<FindFollowerRequest, FindFollowerResponse> {
    @Override
    public FindFollowerResponse handleRequest(FindFollowerRequest request, Context context) {
        FindFollowerServiceImpl service = new FindFollowerServiceImpl();
        return service.findFollower(request);
    }
}
