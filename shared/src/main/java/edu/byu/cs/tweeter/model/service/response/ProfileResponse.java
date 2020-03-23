package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class ProfileResponse {
    private User selectedUser;
    private boolean isFound;

    public ProfileResponse(User selectedUser){
        this.selectedUser = selectedUser;
        if(selectedUser == null){
            isFound = false;
        } else {
            isFound = true;
        }
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public boolean getIsFound() {
        return isFound;
    }

}
