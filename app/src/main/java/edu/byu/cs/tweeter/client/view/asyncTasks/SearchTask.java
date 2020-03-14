package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.ProfileService;
import edu.byu.cs.tweeter.client.view.main.ProfileActivity;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;

public class SearchTask extends AsyncTask<SearchRequest, Void, SearchResponse> {
    private String alias;
    private Activity activity;
//    private MainPresenter presenter;

    public SearchTask(Activity activity, String alias){
        this.alias = alias;
        this.activity = activity;
    }

    @Override
    protected SearchResponse doInBackground(SearchRequest... searchRequests) {
//        ProfileRequest profileRequest = new ProfileRequest(alias);
//        ProfileResponse response = ProfileService.getInstance().getProfileResponse(profileRequest);
//        ProfileService.getInstance().setSelectedUser(response.getSelectedUser());
//        return new SearchResponse(response.getSelectedUser());
        try {
            return ProfileService.getInstance().getSearchResponse(new SearchRequest(alias));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(SearchResponse response){
        if(response.isFound()){
            Intent intent = new Intent(activity, ProfileActivity.class);
            intent.putExtra("userAlias", alias);
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity.getBaseContext(), "Couldn't find " + alias + ".", Toast.LENGTH_SHORT).show();
            System.out.println("NOT FOUND");
        }
    }
}
