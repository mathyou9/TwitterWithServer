package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.client.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.client.view.main.MainLoggedInActivity;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResponse> {
    private final RegisterPresenter presenter;
    private Activity activity;

    public RegisterTask(RegisterPresenter presenter, Activity activity) {
        this.presenter = presenter;
        this.activity = activity;
    }

    @Override
    protected RegisterResponse doInBackground(RegisterRequest... registerRequests){
        RegisterResponse response = null;
        try {
            response = presenter.getRegisteredUser(registerRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginService.getInstance().setCurrentUser(response.getRegisteredUser());
        return response;
//        return null;
    }

    @Override
    protected void onPostExecute(RegisterResponse response){

        Intent intent = new Intent(activity, MainLoggedInActivity.class);
        activity.startActivity(intent);
    }
}
