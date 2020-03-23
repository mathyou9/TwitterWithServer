package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.CreateTweetPresenter;
import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;
import edu.byu.cs.tweeter.model.service.response.CreateTweetResponse;

public class CreateTweetTask extends AsyncTask<CreateTweetRequest, Void, CreateTweetResponse> {

    private final CreateTweetPresenter presenter;
//    private final GetCreateTweetObserver observer;

    public interface GetCreateTweetObserver{
        void createdTweetRetrieved(CreateTweetResponse response);
    }

    public CreateTweetTask(CreateTweetPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    protected CreateTweetResponse doInBackground(CreateTweetRequest... createTweetRequests) {
        CreateTweetResponse response = null;
        try {
            response = presenter.getCreatedTweet(createTweetRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

//    @Override
//    protected void onPostExecute(CreateTweetResponse response){
//        if(observer != null){
//            observer.createdTweetRetrieved(response);
//        }
//    }
}
