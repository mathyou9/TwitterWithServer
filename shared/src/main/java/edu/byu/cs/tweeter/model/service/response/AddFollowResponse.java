package edu.byu.cs.tweeter.model.service.response;

public class AddFollowResponse {
    private boolean didAdd;
    public AddFollowResponse(boolean didAdd){
        this.didAdd = didAdd;
    }

    public boolean isDidAdd() {
        return didAdd;
    }
}
