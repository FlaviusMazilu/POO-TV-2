package commands;

import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.Page;
import utils.User;

public class OnPage extends Command {
    @Override
    public Page execute(Page page, ActionsInput action, UserDataBase userDB, MoviesDataBase moviesDB) {
        return page.onPage(action, userDB);
    }
}
