package edu.byu.cs.tweeter.model.service.response;

public class FindFollowerResponse {
    private boolean isFound;

    public FindFollowerResponse(boolean isFound){
        this.isFound = isFound;
    }

    public boolean isFound() {
        return isFound;
    }
}
