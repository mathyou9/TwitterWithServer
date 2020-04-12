package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.LoginPresenter;
import edu.byu.cs.tweeter.client.view.main.MainLoggedInActivity;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResponse> {

    private final LoginPresenter presenter;
    private Activity activity;

    public LoginTask(LoginPresenter presenter, Activity activity){
        this.presenter = presenter;
        this.activity = activity;
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... loginRequests) {
        LoginResponse response = null;
        try {
            response = presenter.getLoginUser(loginRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(LoginResponse response){
        if(response.getMessage() == null){
            Intent intent = new Intent(activity, MainLoggedInActivity.class);
            activity.startActivity(intent);
        }
    }
}
