package edu.byu.cs.tweeter.client.view.asyncTasks;


import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Observer;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.ProfilePresenter;
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;

public class ProfileTask extends AsyncTask<ProfileRequest, Void, ProfileResponse> {

    private final ProfilePresenter presenter;
    private final Button followButton;
    private final LoadImageTask.LoadImageObserver observer;
    private final TextView username;
    private final TextView alias;

    public ProfileTask(ProfilePresenter presenter, LoadImageTask.LoadImageObserver observer, TextView username, TextView alias, Button followButton){
        this.presenter = presenter;
        this.followButton = followButton;
        this.observer = observer;
        this.username = username;
        this.alias = alias;
    }

    @Override
    protected ProfileResponse doInBackground(ProfileRequest... profileRequests) {
        ProfileResponse response = null;
        try {
            response = presenter.getUserProfile(profileRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(ProfileResponse profileResponse){
        if(presenter.getCurrentUser().getAlias().equals(presenter.getUserProfile().getAlias())){
            followButton.setEnabled(false);
        }
        LoadImageTask loadImageTask = new LoadImageTask(observer);
        loadImageTask.execute(profileResponse.getSelectedUser().getImageUrl());
        username.setText(profileResponse.getSelectedUser().getName());
        alias.setText(profileResponse.getSelectedUser().getAlias());
    }
}
