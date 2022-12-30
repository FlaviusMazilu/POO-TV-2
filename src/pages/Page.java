package pages;
import databases.UserDataBase;
import input.ActionsInput;
import utils.OutputCreater;
public abstract class Page {
    abstract public Page changePage(ActionsInput action);
    abstract public Page onPage(ActionsInput action, UserDataBase userDB);
    abstract public void setPages();
    public void subscribeAction(ActionsInput actions) {
        OutputCreater.addObject("Error", null, null);
    }

    public Page defaultAction() {
        return this;
    }

}
