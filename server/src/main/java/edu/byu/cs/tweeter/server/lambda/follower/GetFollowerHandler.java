package edu.byu.cs.tweeter.server.lambda.follower;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.server.service.FollowerServiceImpl;

public class GetFollowerHandler implements RequestHandler<FollowersRequest, FollowersResponse> {

    @Override
    public FollowersResponse handleRequest(FollowersRequest request, Context context) {
        FollowerServiceImpl service = new FollowerServiceImpl();
        return service.getFollowers(request);
    }
}
