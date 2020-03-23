package edu.byu.cs.tweeter.client.view.main.Story;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.GetStoryTweetsTask;
import edu.byu.cs.tweeter.client.view.cache.ImageCache;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryFragment extends Fragment implements StoryPresenter.View {

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private StoryPresenter presenter;

    private StoryRecyclerViewAdapter storyRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        presenter = new StoryPresenter(this);
        RecyclerView storyRecyclerView = view.findViewById(R.id.storyRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        storyRecyclerView.setLayoutManager(layoutManager);

        storyRecyclerViewAdapter = new StoryRecyclerViewAdapter();
        storyRecyclerView.setAdapter(storyRecyclerViewAdapter);

        storyRecyclerView.addOnScrollListener(new StoryRecyclerViewPaginationScrollListener(layoutManager));

        return view;

    }

    private class StoryHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final TextView userAlias;
        private final TextView message;
        private final ImageView userImage;
        private final TextView tweetDate;

        StoryHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImageTweet);
            userName = itemView.findViewById(R.id.userNameTweet);
            userAlias = itemView.findViewById(R.id.userAliasTweet);
            message = itemView.findViewById(R.id.message);
            tweetDate = itemView.findViewById(R.id.tweetDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "You selected '" + message.getText() + "'.", Toast.LENGTH_SHORT).show();
                }
            });

        }

        void bindTweet(Tweet storyTweet){
            userName.setText(storyTweet.getUserCreated().getName());
            userAlias.setText(storyTweet.getUserCreated().getAlias());
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(storyTweet.getUserCreated()));
            message.setText(storyTweet.getMessage());
            tweetDate.setText(storyTweet.getDate());
            if(storyTweet.getImageUrl() != null){
                //fill in when this is capable of taking images
            }
        }
    }

    private class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryHolder> implements GetStoryTweetsTask.GetStoryTweetsObserver {

        private final List<Tweet> tweets = new ArrayList<>();
        private Tweet lastTweet;

        private boolean hasMorePages;
        private boolean isLoading = false;

        StoryRecyclerViewAdapter() { loadMoreItems(); }

        void loadMoreItems(){
            isLoading = true;
            addLoadingFooter();
            GetStoryTweetsTask getStoryTweetsTask = new GetStoryTweetsTask(presenter, this);

            //may  not work?
            StoryRequest request = new StoryRequest(presenter.getCurrentUser(), PAGE_SIZE, lastTweet);
            getStoryTweetsTask.execute(request);
        }

        void addItems(List<Tweet> newTweets){
            int startInsertPosition = tweets.size();
            tweets.addAll(newTweets);
            this.notifyItemRangeChanged(startInsertPosition, newTweets.size());
        }

        void addItem(Tweet newTweet){
            tweets.add(newTweet);
            this.notifyItemInserted(tweets.size() -1 );
        }

        void removeItem(Tweet tweet){
            int position = tweets.indexOf(tweet);
            tweets.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StoryFragment.this.getContext());
            View view;

            if(isLoading){
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.tweet_row, parent, false);
            }

            return new StoryHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StoryHolder holder, int position) {
            if(!isLoading){
                holder.bindTweet(tweets.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return tweets.size();
        }

        @Override
        public int getItemViewType(int position){
            return (position == tweets.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        @Override
        public void storyTweetsRetrieved(StoryResponse storyResponse) {
            List<Tweet> tweets = storyResponse.getStory();

            lastTweet = (tweets.size() > 0) ? tweets.get(tweets.size() - 1) : null;
            hasMorePages = storyResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            storyRecyclerViewAdapter.addItems(tweets);
        }
        private void addLoadingFooter() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("hh:mm a ' - ' dd MMM YYYY");
            addItem(new Tweet(new User("Dummy", "User", ""), "hello", LocalDateTime.now().format(dateFormatter).toString()));
        }

        private void removeLoadingFooter() {
            removeItem(tweets.get(tweets.size() - 1));
        }
    }

    private class StoryRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        StoryRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager){
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy){
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if(!storyRecyclerViewAdapter.isLoading && storyRecyclerViewAdapter.hasMorePages){
                if((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    storyRecyclerViewAdapter.loadMoreItems();
                }
            }
        }

    }
}
