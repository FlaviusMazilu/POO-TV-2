package pages;
import databases.UserDataBase;
import input.ActionsInput;
import utils.OutputCreater;
public abstract class Page {
    public abstract Page changePage(ActionsInput action);
    public abstract Page onPage(ActionsInput action, UserDataBase userDB);

    /**
     * Method used to set links to the other pages you can go to from the current page
     */
    public abstract void setPages();

    /**
     * Method to override if wanted, by default does print an error if tried to subscribe from
     * a page where you can't subscribe
     * @param actions: action from input
     */
    public void subscribeAction(ActionsInput actions) {
        OutputCreater.addObject("Error", null, null);
    }

}
