package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.client.view.cache.ImageCache;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class GetStoryTweetsTask extends AsyncTask<StoryRequest, Void, StoryResponse> {

    private final StoryPresenter presenter;
    private final GetStoryTweetsObserver observer;

    public interface GetStoryTweetsObserver{
        void storyTweetsRetrieved(StoryResponse storyResponse);
    }

    public GetStoryTweetsTask(StoryPresenter presenter, GetStoryTweetsObserver observer){
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected StoryResponse doInBackground(StoryRequest... storyRequests) {
        StoryResponse response = null;
        try {
            response = presenter.getStoryTweets(storyRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadImages(response);
        return response;
    }

    private void loadImages (StoryResponse response){
        for(Tweet tweet : response.getStory()){
            Drawable drawable;

            try{
                drawable = ImageUtils.drawableFromUrl(tweet.getUserCreated().getImageUrl());
            } catch (IOException e){
                Log.e(this.getClass().getName(), e.toString(), e);
                drawable = null;
            }
            ImageCache.getInstance().cacheImage(tweet.getUserCreated(), drawable);
        }
    }

    @Override
    protected  void onPostExecute(StoryResponse storyResponse){
        if(observer != null){
            observer.storyTweetsRetrieved(storyResponse);
        }
    }
}
