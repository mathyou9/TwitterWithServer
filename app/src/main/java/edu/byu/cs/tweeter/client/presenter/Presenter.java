package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.IFollowingServiceProxy;
import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.client.model.service.ProfileService;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RemoveRequest;
import edu.byu.cs.tweeter.model.service.response.RemoveResponse;

/**
 * A common base class for all presenters in the application.
 */
public abstract class Presenter {

    /**
     * Returns the currently logged in user.
     *
     * @return the user.
     */
    public User getCurrentUser() {
        return LoginService.getInstance().getCurrentUser();
    }

    public User getUserProfile() {
        return ProfileService.getInstance().getSelectedUser();
    }

    public RemoveResponse removeFollowee() throws IOException { return IFollowingServiceProxy.getInstance().removeFollowee(new  RemoveRequest(getCurrentUser(), getUserProfile())); }
}
