package edu.byu.cs.tweeter.server.lambda.following;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.RemoveRequest;
import edu.byu.cs.tweeter.model.service.response.RemoveResponse;
import edu.byu.cs.tweeter.server.service.RemoveFolloweeServiceImpl;

public class RemoveFolloweeHandler implements RequestHandler<RemoveRequest, RemoveResponse> {
    @Override
    public RemoveResponse handleRequest(RemoveRequest removeRequest, Context context) {
        RemoveFolloweeServiceImpl service = new RemoveFolloweeServiceImpl();
        return service.removeFollowee(removeRequest);
    }
}
