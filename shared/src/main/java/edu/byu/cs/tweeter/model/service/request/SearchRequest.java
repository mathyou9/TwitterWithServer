package edu.byu.cs.tweeter.model.service.request;

public class SearchRequest {
    private String alias;

    public SearchRequest(String alias){
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
