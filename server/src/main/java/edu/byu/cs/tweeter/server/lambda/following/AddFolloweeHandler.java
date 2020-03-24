package edu.byu.cs.tweeter.server.lambda.following;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.AddFollowRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowResponse;
import edu.byu.cs.tweeter.server.service.AddFolloweeServiceImpl;

public class AddFolloweeHandler implements RequestHandler<AddFollowRequest, AddFollowResponse> {
    @Override
    public AddFollowResponse handleRequest(AddFollowRequest addFollowRequest, Context context) {
        AddFolloweeServiceImpl service = new AddFolloweeServiceImpl();
        try {
            return service.addFollowee(addFollowRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
