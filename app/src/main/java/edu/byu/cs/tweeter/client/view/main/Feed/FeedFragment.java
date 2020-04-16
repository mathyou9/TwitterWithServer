package edu.byu.cs.tweeter.client.view.main.Feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import edu.byu.cs.tweeter.client.presenter.FeedPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.GetFeedTask;
import edu.byu.cs.tweeter.client.view.cache.ImageCache;
import edu.byu.cs.tweeter.client.view.main.ProfileActivity;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedFragment extends Fragment implements FeedPresenter.View {

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW  = 1;
    private static final int PAGE_SIZE = 10;

    private FeedPresenter presenter;

    private FeedRecyclerViewAdapter feedRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        presenter = new FeedPresenter(this);

        RecyclerView feedRecyclerview = view.findViewById(R.id.feedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        feedRecyclerview.setLayoutManager(layoutManager);

        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter();
        feedRecyclerview.setAdapter(feedRecyclerViewAdapter);

        feedRecyclerview.addOnScrollListener(new FeedRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    private class FeedHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final TextView userAlias;
        private final TextView message;
        private final ImageView userImage;
        private final TextView tweetDate;

        FeedHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImageTweet);
            userName = itemView.findViewById(R.id.userNameTweet);
            userAlias = itemView.findViewById(R.id.userAliasTweet);
            message = itemView.findViewById(R.id.message);
            tweetDate = itemView.findViewById(R.id.tweetDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getContext(), "You selected '" + message.getText() + "'.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra("userAlias", userAlias.getText());
                    getActivity().startActivity(intent);
                }
            });

        }
        void bindTweet(Tweet feedTweet){
            if(userName != null && userAlias != null && message != null && userImage != null){

            }
            userName.setText(feedTweet.getUserCreated().getName());
            userAlias.setText(feedTweet.getUserCreated().getAlias());
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(feedTweet.getUserCreated()));
            message.setText(feedTweet.getMessage());
            tweetDate.setText(feedTweet.getDate());
            if(feedTweet.getImageUrl() != null){
                //fill in later
            }
        }
    }

    private class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedHolder> implements GetFeedTask.GetFeedObserver{

        private final List<Tweet> tweets = new ArrayList<>();

        private Tweet lastTweet;

        private boolean hasMorePages;
        private boolean isLoading = false;

        FeedRecyclerViewAdapter(){
            loadMoreItems();
        }

        void addItems(List<Tweet> newTweets){
            int startInsertPosition = tweets.size();
            tweets.addAll(newTweets);
            this.notifyItemRangeChanged(startInsertPosition, newTweets.size());
        }

        void addItem(Tweet newTweet){
            tweets.add(newTweet);
            this.notifyItemInserted(tweets.size() - 1);
        }

        void removeItem(Tweet tweet){
            int position = tweets.indexOf(tweet);
            tweets.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FeedFragment.this.getContext());
            View view;

            if(isLoading){
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.tweet_row, parent, false);
            }
            return new FeedHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedHolder holder, int position) {
            if(!isLoading){
                holder.bindTweet(tweets.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return tweets.size();
        }

        @Override
        public void feedTweetsRetrieved(FeedResponse feedResponse) {
            List<Tweet> tweets = feedResponse.getTweets();

            lastTweet = (tweets.size() > 0) ? tweets.get(tweets.size() - 1) : null;
            hasMorePages = feedResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            feedRecyclerViewAdapter.addItems(tweets);
        }

        @Override
        public int getItemViewType(int position){
            return (position == tweets.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems(){
            isLoading = true;
            addLoadingFooter();

            GetFeedTask getFeedTask = new GetFeedTask(presenter, this);
            FeedRequest request = new FeedRequest(presenter.getUserProfile(), PAGE_SIZE, lastTweet);
            getFeedTask.execute(request);
        }
        private void addLoadingFooter(){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("hh:mm a ' - ' dd MMM YYYY");
            addItem(new Tweet(new User("Dummy", "User", ""), "hello", LocalDateTime.now().format(dateFormatter).toString()));
        }

        private void removeLoadingFooter(){
            removeItem(tweets.get(tweets.size() - 1));
        }
    }

    private class FeedRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener{
        private final LinearLayoutManager layoutManager;

        FeedRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager){
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy){
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if(!feedRecyclerViewAdapter.isLoading && feedRecyclerViewAdapter.hasMorePages){
                if((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0){
                    feedRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }

}
