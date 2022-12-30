package commands;

import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.Page;

public final class Database extends Command {

    @Override
    public Page execute(final Page page, final ActionsInput action,
                        final UserDataBase userDB, final MoviesDataBase moviesDB) {
        moviesDB.addDeleteMovie(action);
        return page;
    }
}
