package commands;

public class CommandsFactory {
    public static Command createCommand(String command) {
        return switch (command) {
            case "change page" -> new ChangePage();
            case "on page" -> new OnPage();
            case "subscribe" -> new Subscribe();
            case "database" -> new Database();
            case "back" -> new Back();
            default -> null;
        };
    }
}
