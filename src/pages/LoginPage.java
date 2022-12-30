package pages;

import input.ActionsInput;
import utils.User;
import utils.OutputCreater;
import utils.Credentials;
import databases.UserDataBase;

public final class LoginPage extends Page {
    private static LoginPage instance;
    private LoginPage() {
    }
    /**
     * Singleton Pattern for creating only one instance of the Pages.LoginPage class
     * @return returns the only once instance created
     */
    public static LoginPage getInstance() {
        if (instance == null) {
            instance = new LoginPage();
        }
        return instance;
    }

    private Page login(final UserDataBase userDB, final Credentials credentials) {
        User user = userDB.getUser(credentials);
        if (user != null) {
            Authenticated page = Authenticated.getInstance();
            Authenticated.getInstance().setUser(user);
            OutputCreater.addObject(null, null, user);
            return page;

        } else {
            OutputCreater.addObject("Error", null, null);
        }
        return NotAuthenticated.getInstance();
    }

    @Override
    public Page changePage(final ActionsInput action) {
        System.out.println("you can't really changePage, loginPage");
        return NotAuthenticated.getInstance();
    }

    @Override
    public Page onPage(final ActionsInput action, final UserDataBase userDB) {
        if (action.getFeature().compareTo("login") == 0) {
            return login(userDB, new Credentials(action.getCredentials()));
        }
        return NotAuthenticated.getInstance();
    }

    @Override
    public void setPages() {

    }

}
