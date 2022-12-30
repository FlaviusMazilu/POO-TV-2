package commands;

import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.Page;

public abstract class Command {
    /**
     *
     * @param page: current page, can be changed by executing
     * @param action: action read from input
     * @param userDB: The database of users
     * @param moviesDb: The database of movies
     * @return same page as input/another page
     */
    public abstract Page execute(Page page, ActionsInput action,
                                 UserDataBase userDB, MoviesDataBase moviesDb);
}
