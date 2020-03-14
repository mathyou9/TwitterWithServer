package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.client.model.service.ProfileService;
import edu.byu.cs.tweeter.model.domain.User;

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
}
