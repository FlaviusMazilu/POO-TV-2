package pages;
import databases.UserDataBase;
import input.ActionsInput;
import utils.OutputCreater;
public abstract class Page {
    abstract public Page changePage(ActionsInput action);
    abstract public Page onPage(ActionsInput action, UserDataBase userDB);
    abstract public void setPages();
    public void subscribeAction(String subscribedGenre) {
        OutputCreater.addObject("Cannot subscribe from here", null, null);
    }

}
