package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.client.view.cache.ImageCache;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;

public class GetFollowerTask extends AsyncTask<FollowersRequest, Void, FollowersResponse> {

    private final FollowerPresenter presenter;
    private final GetFollowerObserver observer;

    public interface GetFollowerObserver{
        void followersRetrieved(FollowersResponse followersResponse);
    }

    public GetFollowerTask(FollowerPresenter presenter, GetFollowerObserver observer){
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowersResponse doInBackground(FollowersRequest... followersRequests) {
        FollowersResponse response = null;
        try {
            response = presenter.getFollowers(followersRequests[0]);
            if(response != null && response.getFollowers() != null){
                loadImages(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void loadImages(FollowersResponse response){
        for(User user : response.getFollowers()){

            Drawable drawable;

            try{
                drawable = ImageUtils.drawableFromUrl(user.getImageUrl());
            } catch (IOException e){
                Log.e(this.getClass().getName(), e.toString(), e);
                drawable = null;
            }

            ImageCache.getInstance().cacheImage(user, drawable);
        }
    }

    @Override
    protected void onPostExecute(FollowersResponse followersResponse){
        if(observer != null){
            observer.followersRetrieved(followersResponse);
        }
    }

}
