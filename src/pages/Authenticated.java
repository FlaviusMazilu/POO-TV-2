package pages;

import input.ActionsInput;
import utils.User;
import utils.OutputCreater;
import databases.UserDataBase;

import java.util.HashMap;

public final class Authenticated extends Page {

    private static Authenticated instance;
    private final HashMap<String, Page> pages;
    private User user;

    private Authenticated() {
        pages = new HashMap<>();
    }

    /**
     * Singleton Pattern for creating only one instance of the Pages.Authenticated class
     * @return returns the only once instance created
     */
    public static Authenticated getInstance() {
        if (instance == null) {
            instance = new Authenticated();
        }
        return instance;
    }

    @Override
    public Page changePage(final ActionsInput action) {
        if (!pages.containsKey(action.getPage())) {
            OutputCreater.addObject("Error", null, null);
            return this;
        } else {
            return pages.get(action.getPage());
        }
    }

    @Override
    public Page onPage(final ActionsInput action, final UserDataBase userDB) {
        OutputCreater.addObject("Error", null, null);
        return this;
    }

    @Override
    public void setPages() {
        pages.put("movies", MoviesPage.getInstance());
        pages.put("upgrades", Upgrades.getInstance());
        pages.put("logout", LogoutPage.getInstance());
    }


    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
