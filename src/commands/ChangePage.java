package commands;

import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.Authenticated;
import pages.MoviesPage;
import pages.Page;
import utils.OutputCreater;

public class ChangePage extends Command{

    @Override
    public Page execute(Page page, ActionsInput action, UserDataBase userDB, MoviesDataBase moviesDB) {
        if (page instanceof MoviesPage && action.getPage().equals("movies")) {
            MoviesPage.getInstance().getAllMovies();
            OutputCreater.addObject(null, MoviesPage.getInstance().getMovies(),
                    Authenticated.getInstance().getUser());
        }
        return page;
    }
}
