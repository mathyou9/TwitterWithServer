package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.request.SearchRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;
import edu.byu.cs.tweeter.model.service.response.SearchResponse;

public class ProfileService {

    private static final String URL_PATH = "/getProfile";

    private static ProfileService instance;

    private final ServerFacade serverFacade;

    private User selectedUser;

    public static ProfileService getInstance(){
        if(instance == null){
            instance= new ProfileService();
        }
        return instance;
    }

    private ProfileService(){
        serverFacade = new ServerFacade();
    }

    public ProfileResponse getProfileResponse(ProfileRequest request) throws IOException {
        ProfileResponse profileResponse = serverFacade.getUserProfile(request, URL_PATH);
        setSelectedUser(profileResponse.getSelectedUser());
        return profileResponse;
    }
    public User getSelectedUser(){
        return selectedUser;
    }
    public void setSelectedUser(User selectedUser){
        this.selectedUser = selectedUser;
    }

    public SearchResponse getSearchResponse(SearchRequest request) throws IOException {
        ProfileResponse profileResponse = getProfileResponse(new ProfileRequest(request.getAlias()));
        return new SearchResponse(profileResponse.getSelectedUser());
    }

}
