package pages;

import input.ActionsInput;

import java.util.HashMap;
import utils.Cons;
import utils.OutputCreater;
import utils.User;
import databases.UserDataBase;
public final class Upgrades extends Page {
    private static Upgrades instance;
    private HashMap<String, Page> pages;

    private Upgrades() {
        pages = new HashMap<>();
    }

    /**
     * Singleton Pattern for creating only one instance of the Pages.Upgrades class
     * @return returns the only once instance created
     */
    public static Upgrades getInstance() {
        if (instance == null) {
            instance = new Upgrades();
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

    private void buyPremium() {
        User currentUser = Authenticated.getInstance().getUser();
        int tokens = currentUser.getTokensCount();
        if (tokens < Cons.PRICE_PREMIUM) {
            OutputCreater.addObject("Error", null, null);
            return;
        }
        currentUser.setTokensCount(tokens - Cons.PRICE_PREMIUM);
        currentUser.getCredentials().setAccountType("premium");
    }

    private void buyTokens(final int count) {
        User currentUser = Authenticated.getInstance().getUser();
        int balance = currentUser.getCredentials().convertBalanceInt();
        if (balance < count) {
            OutputCreater.addObject("Error", null, null);
            return;
        }
        currentUser.getCredentials().setBalance(balance - count);
        currentUser.setTokensCount(currentUser.getTokensCount() + count);
    }

    @Override
    public Page onPage(final ActionsInput action, final UserDataBase userDB) {
        switch (action.getFeature()) {
            case "buy premium account" -> buyPremium();
            case "buy tokens" -> buyTokens(action.getCount());
            default -> OutputCreater.addObject("Error", null, null);
        }
        return this;
    }

    @Override
    public void setPages() {
        pages.put("homepage autentificat", Authenticated.getInstance());
        pages.put("movies", MoviesPage.getInstance());
        pages.put("logout", LogoutPage.getInstance());
    }

}

