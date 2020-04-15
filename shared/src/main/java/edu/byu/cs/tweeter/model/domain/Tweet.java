package edu.byu.cs.tweeter.model.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tweet {
    String message;
    User userCreated;
    String imageUrl;
    String date;
    String tweetID;

    public Tweet(){}
    public Tweet(User userCreated, String message, String date){
        this.message = message;
        this.userCreated = userCreated;
        this.date = date;
    }
    public String getMessage() {return message;}
    public String getImageUrl() {return imageUrl;}
    public User getUserCreated() {return userCreated;}

    public String getDate() {
        return this.date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

    public String getTweetID() {
        return tweetID;
    }
}
