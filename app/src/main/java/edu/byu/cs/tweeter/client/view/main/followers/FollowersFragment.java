package edu.byu.cs.tweeter.client.view.main.followers;

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

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.GetFollowerTask;
import edu.byu.cs.tweeter.client.view.cache.ImageCache;
import edu.byu.cs.tweeter.client.view.main.ProfileActivity;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;


public class FollowersFragment extends Fragment implements FollowerPresenter.View {

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private FollowerPresenter presenter;

    private FollowerRecyclerViewAdapter followerRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_follower, container, false);
        presenter = new FollowerPresenter(this);
        RecyclerView followerRecyclerView = view.findViewById(R.id.followingRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        followerRecyclerView.setLayoutManager(layoutManager);

        followerRecyclerViewAdapter = new FollowerRecyclerViewAdapter();
        followerRecyclerView.setAdapter(followerRecyclerViewAdapter);

        followerRecyclerView.addOnScrollListener(new FollowRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    private class FollowerHolder extends RecyclerView.ViewHolder{
        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;

        FollowerHolder(@NonNull View itemView){
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(getContext(), "You selected '" + userName.getText() + "'.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtra("userAlias", userAlias.getText());
                    getActivity().startActivity(intent);
                }
            });
        }

        void bindUser(User user){
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(user));
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());
        }
    }

    private class FollowerRecyclerViewAdapter extends RecyclerView.Adapter<FollowerHolder> implements GetFollowerTask.GetFollowerObserver {
        private final List<User> users = new ArrayList<>();
        private User lastFollower;

        private boolean hasMorePages;
        private boolean isLoading = false;

        FollowerRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<User> newUsers) {
            int startInsertPosition = users.size();
            users.addAll(newUsers);
            this.notifyItemRangeInserted(startInsertPosition, newUsers.size());
        }

        void addItem(User user) {
            users.add(user);
            this.notifyItemInserted(users.size() - 1);
        }

        void removeItem(User user) {
            int position = users.indexOf(user);
            users.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public FollowerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FollowersFragment.this.getContext());
            View view;

            if (isLoading) {
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.user_row, parent, false);
            }
            return new FollowerHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FollowerHolder holder, int position) {
            if (!isLoading) {
                holder.bindUser(users.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == users.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems(){
            isLoading = true;
            addLoadingFooter();

            GetFollowerTask getFollowerTask = new GetFollowerTask(presenter, this);
            String title = getActivity().getTitle().toString();
            FollowersRequest request;
            if(title.equals("Tweeter")){
                request = new FollowersRequest(presenter.getCurrentUser(), PAGE_SIZE, lastFollower);
            } else {
                request = new FollowersRequest(presenter.getUserProfile(), PAGE_SIZE, lastFollower);
            }
            getFollowerTask.execute(request);

        }

        @Override
        public void followersRetrieved(FollowersResponse followersResponse) {
            List<User> followers = followersResponse.getFollowers();
            lastFollower = (followers.size() > 0) ? followers.get(followers.size() -1) : null;
            hasMorePages = followersResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            followerRecyclerViewAdapter.addItems(followers);
        }

        private void addLoadingFooter(){
            addItem(new User("DUMMY", "USER", ""));
        }
        private void removeLoadingFooter() {
            removeItem(users.get(users.size() - 1));
        }

    }

    private class FollowRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener{
        private final LinearLayoutManager layoutManager;

        FollowRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager){
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy){
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if(!followerRecyclerViewAdapter.isLoading && followerRecyclerViewAdapter.hasMorePages){
                if((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0){
                    followerRecyclerViewAdapter.loadMoreItems();
                }
            }

        }
    }

}
