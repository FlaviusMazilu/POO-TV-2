package pages;
import databases.UserDataBase;
import input.ActionsInput;

public final class LogoutPage extends Page {
    private static LogoutPage instance;
    private static Page page;
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

    private void logout() {
        page = NotAuthenticated.getInstance();
        Authenticated.getInstance().setUser(null);
    }
    @Override
    public Page changePage(final ActionsInput action) {
        return this;
    }

    /**
     * The default method that is executed when switching to Pages.LogoutPage
     * @return Pages.NotAuthenticated page
     */
    public Page actionLogout() {
        logout();
        return NotAuthenticated.getInstance();
    }

    @Override
    public Page onPage(final ActionsInput action, final UserDataBase userDB) {
        return this;
    }

    @Override
    public void setPages() {

    }

}
