package commands;

import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.Page;

public class Database extends Command{

    @Override
    public Page execute(Page page, ActionsInput action, UserDataBase userDB, MoviesDataBase moviesDB) {
        moviesDB.addDeleteMovie(action);
        return page;
    }
}
