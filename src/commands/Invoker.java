package commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.CommandsFactory;
import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.*;
import utils.IO;
import utils.Notification;
import utils.OutputCreater;
import utils.User;

import java.util.ArrayList;
import java.util.LinkedList;

public class Invoker {
    ArrayList<Page> previousPages;
    private UserDataBase userDB;
    private MoviesDataBase moviesDB;
    private Page page;
    private LinkedList<Page> pagesStack;
    public Invoker(UserDataBase userDB, MoviesDataBase moviesDB) {
        previousPages = new ArrayList<>();
        pagesStack = new LinkedList<>();
        this.userDB = userDB;
        this.moviesDB = moviesDB;
        this.page = NotAuthenticated.getInstance();
    }

    public void getRecommandation() {
        User user = Authenticated.getInstance().getUser();

        if (user != null && user.getCredentials().getAccountType().equals("premium")) {
            String movie = user.checkMovieRecommended();
            Notification notification = new Notification(movie, "Recommendation");
            user.update(notification);

            ObjectNode objectNode = IO.objectMapper.createObjectNode();
            objectNode.put("error", (JsonNode) null);
            objectNode.put("currentMoviesList", (JsonNode) null);
            objectNode.putPOJO("currentUser", new User(user));
            IO.output.add((objectNode));
        }
    }
    public void execute(ActionsInput action) {
        if (page instanceof LogoutPage) {
            Authenticated.getInstance().setUser(null);
            page = NotAuthenticated.getInstance();
            // the number of pages that you can go back to turns to 0
            pagesStack = new LinkedList<>();
        }

        if (action.getType().equals("change page")) {
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

            if (page instanceof MoviesPage) {
                MoviesPage.getInstance().getAllMovies();
                OutputCreater.addObject(null, MoviesPage.getInstance().getMovies(),
                        Authenticated.getInstance().getUser());
            }
            return;
        }
        String command = action.getType();
        page = CommandsFactory.createCommand(command).execute(page, action, userDB, moviesDB);
    }


}