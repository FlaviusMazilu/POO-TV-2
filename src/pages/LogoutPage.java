package pages;
import databases.UserDataBase;
import input.ActionsInput;

public final class LogoutPage extends Page {
    private static LogoutPage instance;

    private LogoutPage() {

    }

    /**
     * Singleton Pattern for creating only one instance of the Pages.LogoutPage class
     * @return returns the only once instance created
     */
    public static LogoutPage getInstance() {
        if (instance == null) {
            instance = new LogoutPage();
        }

        return instance;
    }

    @Override
    public Page changePage(final ActionsInput action) {
        return this;
    }

    /**
     * The default method that is executed when switching to Pages.LogoutPage
     * @return Pages.NotAuthenticated page
     */
    @Override
    public Page onPage(final ActionsInput action, final UserDataBase userDB) {
        return this;
    }

    @Override
    public void setPages() {

    }

}
