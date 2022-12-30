package commands;

public abstract class CommandsFactory {
    /**
     *
     * @param command: a string with the name of the command that is wanted to be created
     * @return an upcast to a Command of a specific command created for the parameter string
     */
    public static Command createCommand(final String command) {
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
