package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class SearchResponse {
    private User user;
    private boolean isFound;

    public SearchResponse(User user){
        this.user = user;
        if(user == null){
            isFound = false;
        } else {
            isFound = true;
        }
    }

    public User getUser() {
        return user;
    }

    public boolean isFound() {
        return isFound;
    }
}
