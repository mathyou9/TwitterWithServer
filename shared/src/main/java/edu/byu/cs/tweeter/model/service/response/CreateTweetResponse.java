package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class CreateTweetResponse extends PagedResponse {

    private User user;
    private String message;

    public CreateTweetResponse (String message) {super(false, message, false);}

    public CreateTweetResponse (User user, String message){
        super(true, false);
        this.user = user;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
