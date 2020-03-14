package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;

public class FollowerPresenter extends Presenter{
    private final View view;

    public interface View{

    }

    public FollowerPresenter(View view) {this.view = view;}

    public FollowersResponse getFollowers(FollowersRequest request) throws IOException {
        return FollowersService.getInstance().getFollowers(request);
    }
}
