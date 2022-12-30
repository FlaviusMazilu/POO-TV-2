package commands;

import databases.MoviesDataBase;
import databases.UserDataBase;
import input.ActionsInput;
import pages.Page;

public final class Subscribe extends Command {
    @Override
    public Page execute(final Page page, final ActionsInput action,
                        final UserDataBase userDB, final MoviesDataBase mDB) {
        page.subscribeAction(action);
        return page;
    }
}
