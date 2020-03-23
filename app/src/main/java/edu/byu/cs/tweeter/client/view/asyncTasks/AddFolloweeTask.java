package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.IFollowingServiceProxy;
import edu.byu.cs.tweeter.client.presenter.ProfilePresenter;
import edu.byu.cs.tweeter.model.service.request.AddFollowRequest;
import edu.byu.cs.tweeter.model.service.response.AddFollowResponse;

public class AddFolloweeTask extends AsyncTask<AddFollowRequest, Void, AddFollowResponse> {

    private ProfilePresenter presenter;
    private Activity activity;

    public AddFolloweeTask(ProfilePresenter presenter, Activity activity){
        this.presenter = presenter;
        this.activity = activity;
    }

    @Override
    protected AddFollowResponse doInBackground(AddFollowRequest... addFollowRequests) {
        try {
            return IFollowingServiceProxy.getInstance().addFollowee(addFollowRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
