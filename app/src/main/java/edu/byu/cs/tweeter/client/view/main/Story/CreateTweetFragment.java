package edu.byu.cs.tweeter.client.view.main.Story;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.CreateTweetPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.CreateTweetTask;
import edu.byu.cs.tweeter.model.service.request.CreateTweetRequest;

public class CreateTweetFragment extends Fragment implements CreateTweetPresenter.View {

    private CreateTweetPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.create_tweet, container, false);

        presenter = new CreateTweetPresenter(this);

        RelativeLayout background = view.findViewById(R.id.exitRelativeLayout);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateTweetFragment f = (CreateTweetFragment) getFragmentManager().findFragmentByTag("tweetFrag");
                getFragmentManager().beginTransaction().remove(f).commit();
            }
        });
        ImageView userImage = view.findViewById(R.id.createTweetImage);
        TextView userAlias = view.findViewById(R.id.createTweetAlias);
        TextView userName = view.findViewById(R.id.createTweetName);
        final EditText message = view.findViewById(R.id.createTweetMessage);
        Button shareButton = view.findViewById(R.id.createTweetShare);

        userAlias.setText(presenter.getCurrentUser().getAlias());
        userName.setText(presenter.getCurrentUser().getName());


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CreateTweetResponse response = presenter.getCreatedTweet(new CreateTweetRequest(presenter.getCurrentUser(), message.getText().toString()));
                //System.out.println(response.getMessage());
                CreateTweetTask createTweetTask = new CreateTweetTask(presenter);
                CreateTweetRequest request = new CreateTweetRequest(presenter.getCurrentUser(), message.getText().toString());
                createTweetTask.execute(request);
            }
        });



        return view;
    }

}
