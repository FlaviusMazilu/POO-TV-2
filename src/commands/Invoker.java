package commands;

import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.Page;
import pages.LogoutPage;
import pages.NotAuthenticated;
import pages.Authenticated;

import utils.Notification;
import utils.OutputCreater;
import utils.User;

import java.util.ArrayList;
import java.util.LinkedList;

public final class Invoker {
    ArrayList<Page> previousPages;
    private final UserDataBase userDB;
    private final MoviesDataBase moviesDB;
    private Page page;
    private LinkedList<Page> pagesStack;
    public Invoker(final UserDataBase userDB, final MoviesDataBase moviesDB) {
        previousPages = new ArrayList<>();
        pagesStack = new LinkedList<>();
        this.userDB = userDB;
        this.moviesDB = moviesDB;
        this.page = NotAuthenticated.getInstance();
    }

    /**
     * Method to construct a recommendation for the logged-in user if it's a premium account
     */
    public void getRecommandation() {
        User user = Authenticated.getInstance().getUser();

        if (user != null && user.getCredentials().getAccountType().equals("premium")) {
            String movie = user.checkMovieRecommended();
            Notification notification = new Notification(movie, "Recommendation");
            user.update(notification, null);

            OutputCreater.addObject(user);
        }
    }

    /**
     * Method which executes the command specified at action.getType()
     * @param action: action from input
     */
    public void execute(final ActionsInput action) {
        if (page instanceof LogoutPage) {
            Authenticated.getInstance().setUser(null);
            page = NotAuthenticated.getInstance();
            // the number of pages that you can go back to turns to 0
            pagesStack = new LinkedList<>();
        }

        if (action.getType().equals("change page")) {
            // special case ofr change page command, the
            if (Authenticated.getInstance().getUser() != null && pagesStack.size() == 0) {
                pagesStack.push(Authenticated.getInstance());
            }

            page = page.changePage(action);
            if (Authenticated.getInstance().getUser() != null && page != pagesStack.peek()) {
                pagesStack.push(page);
            }
        }
        if (action.getType().equals("back")) {
            if (pagesStack.size() <= 1) {
                OutputCreater.addObject("Error", null, null);
                return;
            }
            pagesStack.pop();
            page = pagesStack.peek();
        }
        String command = action.getType();
        page = CommandsFactory.createCommand(command).execute(page, action, userDB, moviesDB);
    }
}
