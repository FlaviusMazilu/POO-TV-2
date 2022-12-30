package commands;

import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.Authenticated;
import pages.MoviesPage;
import pages.Page;
import utils.OutputCreater;

public class Back extends Command{
    @Override
    public Page execute(Page page, ActionsInput action, UserDataBase userDB, MoviesDataBase moviesDb) {
        if (page instanceof MoviesPage) {
            MoviesPage.getInstance().getAllMovies();
            OutputCreater.addObject(null, MoviesPage.getInstance().getMovies(),
                    Authenticated.getInstance().getUser());
        }
        return page;
    }
}
