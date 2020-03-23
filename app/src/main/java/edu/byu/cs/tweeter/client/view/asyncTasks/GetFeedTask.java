package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.FeedPresenter;
import edu.byu.cs.tweeter.client.view.cache.ImageCache;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class GetFeedTask extends AsyncTask<FeedRequest, Void, FeedResponse> {

    private final FeedPresenter presenter;
    private final GetFeedObserver observer;

    public interface GetFeedObserver{
        void feedTweetsRetrieved(FeedResponse feedResponse);
    }

    public GetFeedTask(FeedPresenter presenter, GetFeedObserver observer){
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FeedResponse doInBackground(FeedRequest... feedRequests) {
        FeedResponse response = null;
        try {
            response = presenter.getFeed(feedRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadImages(response);
        return response;
    }

    private void loadImages(FeedResponse response){
        for(Tweet tweet : response.getTweets()){
            Drawable drawable;
            if(tweet.getImageUrl() != null){
                try{
                    drawable = ImageUtils.drawableFromUrl(tweet.getUserCreated().getImageUrl());
                } catch (IOException e){
                    Log.e(this.getClass().getName(), e.toString(), e);
                    drawable = null;
                }
                ImageCache.getInstance().cacheImage(tweet.getUserCreated(), drawable);
            }
        }
    }

    @Override
    protected void onPostExecute(FeedResponse feedResponse){
        if(observer != null){
            observer.feedTweetsRetrieved(feedResponse);
        }
    }
}
