package edu.byu.cs.tweeter.model.service.response;

public class RemoveResponse {
    boolean didRemove;
    public RemoveResponse(boolean didRemove){
        this.didRemove = didRemove;
    }

    public boolean isDidRemove() {
        return didRemove;
    }
}
