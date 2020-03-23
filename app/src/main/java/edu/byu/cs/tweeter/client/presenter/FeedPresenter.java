package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedPresenter extends Presenter {
    private final View view;

    public interface View{

    }
    public FeedPresenter(View view){
        this.view = view;
    }

    public FeedResponse getFeed(FeedRequest request) throws IOException {
        return FeedService.getInstance().getTweets(request);
    }
}
