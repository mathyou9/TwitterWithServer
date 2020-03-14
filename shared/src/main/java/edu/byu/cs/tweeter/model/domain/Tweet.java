package edu.byu.cs.tweeter.model.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tweet {
    String message;
    User created;
    String imageUrl;
    LocalDateTime date;

    public Tweet(User created, String message, LocalDateTime date){
        this.message = message;
        this.created = created;
        this.date = date;
    }
    public String getMessage() {return message;}
    public String getImageUrl() {return imageUrl;}
    public User getUserCreated() {return created;}

    public String getDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("hh:mm a ' - ' dd MMM YYYY");
        return date.format(dateFormatter).toString();
    }
}
