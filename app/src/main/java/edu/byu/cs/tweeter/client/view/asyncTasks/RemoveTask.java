package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.IFollowingServiceProxy;
import edu.byu.cs.tweeter.client.presenter.Presenter;
import edu.byu.cs.tweeter.client.presenter.ProfilePresenter;
import edu.byu.cs.tweeter.model.service.request.RemoveRequest;
import edu.byu.cs.tweeter.model.service.response.RemoveResponse;

public class RemoveTask extends AsyncTask<RemoveRequest, Void, RemoveResponse> {

    private ProfilePresenter presenter;
    private Activity activity;

    public RemoveTask(ProfilePresenter presenter, Activity activity){
        this.presenter = presenter;
        this.activity = activity;
    }

    @Override
    protected RemoveResponse doInBackground(RemoveRequest... removeRequests) {
        try {
            return IFollowingServiceProxy.getInstance().removeFollowee(removeRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
