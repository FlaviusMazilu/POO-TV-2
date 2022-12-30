package commands;

import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.Page;

public abstract class Command {
    public abstract Page execute(Page page, ActionsInput action, UserDataBase userDB, MoviesDataBase moviesDb);
}
