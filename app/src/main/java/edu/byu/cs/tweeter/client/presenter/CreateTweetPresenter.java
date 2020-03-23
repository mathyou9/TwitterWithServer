package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.CreateTweetService;
import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;
import edu.byu.cs.tweeter.model.service.response.CreateTweetResponse;

public class CreateTweetPresenter extends Presenter {
    private final View view;

    public interface View{

    }

    public CreateTweetPresenter(View view){
        this.view = view;
    }

    public CreateTweetResponse getCreatedTweet (CreateTweetRequest request) throws IOException {
        return CreateTweetService.getInstance().getTweetResponse(request);
    }

}
