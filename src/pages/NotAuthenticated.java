package pages;

import input.ActionsInput;
import utils.OutputCreater;
import databases.UserDataBase;
import java.util.HashMap;

public class NotAuthenticated extends Page {
    private static NotAuthenticated instance;
    private HashMap<String, Page> pages;

    private NotAuthenticated() {
        pages = new HashMap<>();
    }
    public static NotAuthenticated getInstance() {
        if (instance == null) {
            instance = new NotAuthenticated();
        }
        return instance;
    }

    @Override
    public Page changePage(final ActionsInput action) {
        if (!pages.containsKey(action.getPage())) {
            OutputCreater.addObject("Error", null, null);
            return this;
        }
        return pages.get(action.getPage());
    }

    @Override
    public Page onPage(final ActionsInput action, final UserDataBase userDB) {
        System.out.println("you cannot have OnPage action on notAuthenticated page");
        return this;
    }

    @Override
    public void setPages() {
        pages.put("login", LoginPage.getInstance());
        pages.put("register", RegisterPage.getInstance());
    }

}
