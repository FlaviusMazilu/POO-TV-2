package commands;

import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.Page;

public class Subscribe extends Command{
    @Override
    public Page execute(Page page, ActionsInput action, UserDataBase userDB, MoviesDataBase moviesDB) {
        page.subscribeAction(action);
        return page;
    }
}
