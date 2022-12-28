package pages;

import input.ActionsInput;
import utils.OutputCreater;
import utils.Credentials;
import utils.User;
import databases.UserDataBase;

public final class RegisterPage extends Page {
    private static RegisterPage instance;

    private RegisterPage() {
    }
    /**
     * Singleton Pattern for creating only one instance of the Pages.RegisterPage class
     * @return returns the only once instance created
     */
    public static RegisterPage getInstance() {
        if (instance == null) {
            instance = new RegisterPage();
        }
        return instance;
    }
    private Page register(final UserDataBase userDB, final Credentials credentials) {
        User user = new User(credentials);
        if (userDB.hasUser(credentials.getName())) {
            OutputCreater.addObject("Error name taken", null, null);
            return NotAuthenticated.getInstance();
        }
        userDB.addUser(user);
        OutputCreater.addObject(null, null, user);
        Page page = Authenticated.getInstance();
        Authenticated.getInstance().setUser(user);
        return page;
    }

    @Override
    public Page changePage(final ActionsInput action) {
        return NotAuthenticated.getInstance();
    }

    @Override
    public Page onPage(final ActionsInput action, final UserDataBase userDB) {
        if (action.getFeature().compareTo("register") == 0) {
            return register(userDB, new Credentials(action.getCredentials()));
        }

        return NotAuthenticated.getInstance();
    }

    @Override
    public void setPages() {

    }

}
