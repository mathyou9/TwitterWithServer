package edu.byu.cs.tweeter.model.service.request;

public class SearchRequest {
    private String alias;

    public SearchRequest(){}
    public SearchRequest(String alias){
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
