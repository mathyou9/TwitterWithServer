package edu.byu.cs.tweeter.client.view.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.model.service.FollowingServiceProxy;
import edu.byu.cs.tweeter.client.presenter.ProfilePresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.client.view.asyncTasks.SearchTask;
import edu.byu.cs.tweeter.client.view.cache.ImageCache;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.request.AddFollowRequest;
import edu.byu.cs.tweeter.model.service.request.FindFollowerRequest;
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.request.RemoveRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;

public class ProfileActivity extends AppCompatActivity implements LoadImageTask.LoadImageObserver, ProfilePresenter.View {

    private ProfilePresenter presenter;
    private User user;
    private ImageView userImageView;

    public ProfileActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        presenter = new ProfilePresenter(this);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        final Button followButton = findViewById(R.id.followButton);

        userImageView = findViewById(R.id.userImage);

        String alias = getIntent().getStringExtra("userAlias");
//        ProfileTask profileTask = new ProfileTask(presenter);
//        profileTask.execute(new ProfileRequest(alias));
        try {
            ProfileResponse response = presenter.getUserProfile(new ProfileRequest(alias));
        } catch (IOException e) {
            e.printStackTrace();
        }
        user = presenter.getUserProfile();
//        ViewGroup view1 = (ViewGroup) tabs.getChildAt(0);
        final View tab = ((ViewGroup) tabs.getChildAt(0)).getChildAt(0);
        final View tab2 = ((ViewGroup) tabs.getChildAt(0)).getChildAt(1);
        FindFollowerRequest request = new FindFollowerRequest(presenter.getCurrentUser(), presenter.getUserProfile());
        try {
            if(FollowingServiceProxy.getInstance().isFollowing(request).isFound()){
                followButton.setText("Unfollow");
            } else {
                followButton.setText("Follow");
    //            tabs.getChildAt(0).setEnabled(false);
    //            tabs.getChildAt(1).setEnabled(false);
                tab.setEnabled(false);
                tab2.setEnabled(false);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(presenter.getCurrentUser().getAlias().equals(presenter.getUserProfile().getAlias())){
            followButton.setEnabled(false);
        }
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followButton.getText().equals("Unfollow")){
                    followButton.setText("Follow");
                    RemoveRequest request = new RemoveRequest(presenter.getUserProfile(), presenter.getCurrentUser());
                    try {
                        FollowingServiceProxy.getInstance().removeFollowee(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    tab.setEnabled(false);
                    tab2.setEnabled(false);
                } else {
                    followButton.setText("Unfollow");
                    AddFollowRequest request = new AddFollowRequest(presenter.getCurrentUser(), presenter.getUserProfile());
                    try {
                        FollowingServiceProxy.getInstance().addFollowee(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    tabs.getChildAt(0).setEnabled(true);
//                    tabs.getChildAt(1).setEnabled(true);
                    tab.setEnabled(true);
                    tab2.setEnabled(true);
                }
            }
        });

        LoadImageTask loadImageTask = new LoadImageTask(this);
        loadImageTask.execute(user.getImageUrl());

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);

    }

    @Override
    public void imageLoadProgressUpdated(Integer progress) {

    }

    @Override
    public void imagesLoaded(Drawable[] drawables) {
        ImageCache.getInstance().cacheImage(user, drawables[0]);

        if(drawables[0] != null) {
            userImageView.setImageDrawable(drawables[0]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                SearchTask searchTask = new SearchTask(query);
//                SearchRequest request = new SearchRequest(query);
//                searchTask.execute();
                System.out.println(query);
                SearchTask searchTask = new SearchTask(ProfileActivity.this, query);
                searchTask.execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
