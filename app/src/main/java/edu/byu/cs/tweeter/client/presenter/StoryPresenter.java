package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryPresenter extends Presenter {

    private  final View view;

    public interface View{

    }

    public StoryPresenter(View view) {this.view = view;}
    public StoryResponse getStoryTweets(StoryRequest request) throws IOException {
        return StoryService.getInstance().getTweets(request);
    }

}
