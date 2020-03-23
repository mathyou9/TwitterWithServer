package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LogoutService;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutTask extends AsyncTask<LogoutRequest, Void, LogoutResponse> {

    private Activity activity;
    private User user;

    public LogoutTask(Activity activity, User user){
        this.activity = activity;
        this.user = user;
    }

    @Override
    protected LogoutResponse doInBackground(LogoutRequest... logoutRequests) {
        LogoutResponse response = null;
        try {
            response = LogoutService.getInstance().logout(new LogoutRequest(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    @Override
    protected void onPostExecute(LogoutResponse response){
        if(response.didLogout){
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        } else {

            Toast.makeText(activity.getBaseContext(), "Couldn't logout, please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
