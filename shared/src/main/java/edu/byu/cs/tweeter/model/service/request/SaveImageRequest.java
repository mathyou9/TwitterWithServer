package edu.byu.cs.tweeter.model.service.request;

public class SaveImageRequest {
    private String base64Image;
    private String alias;

    public SaveImageRequest(){}
    public SaveImageRequest(String base64Image, String alias){
        this.base64Image = base64Image;
        this.alias = alias;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
