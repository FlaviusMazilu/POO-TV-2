package commands;

import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.Authenticated;
import pages.MoviesPage;
import pages.Page;
import utils.OutputCreater;

public final class Back extends Command {
    @Override
    public Page execute(final Page page, final ActionsInput action,
                        final UserDataBase userDB, final MoviesDataBase moviesDb) {

        if (page instanceof MoviesPage) {
            MoviesPage.getInstance().getAllMovies();
            OutputCreater.addObject(null, MoviesPage.getInstance().getMovies(),
                    Authenticated.getInstance().getUser());
        }
        return page;
    }
}
