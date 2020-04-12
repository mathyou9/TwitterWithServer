package edu.byu.cs.tweeter.model.service.response;

public class SaveImageResponse {
    private String url;

    public SaveImageResponse(){}
    public SaveImageResponse(String url){
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
