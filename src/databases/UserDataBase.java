package databases;

import utils.Credentials;
import utils.User;
import java.util.HashMap;

public final class UserDataBase {
    private HashMap<String, User> database;

    public UserDataBase() {
        database = new HashMap<>();
    }

    public HashMap<String, User> getDatabase() {
        return database;
    }

    public void setDatabase(final HashMap<String, User> database) {
        this.database = database;
    }

    /**
     * Method to test if a user is in the database based on his name
     * @param userName name of the user to be tested if it's in the database
     * @return true if user exists, else returns false
     */
    public boolean hasUser(final String userName) {
        return database.containsKey(userName);
    }

    /**
     * It adds a user to the database if one with the same name doesn't already exist
     * @param user user to be added
     */
    public void addUser(final User user) {
        if (database.containsKey(user.getCredentials().getName())) {
            return;
        }
        database.put(user.getCredentials().getName(), user);
    }

    /**
     * @param credentials name & password of the user
     * @return if Utils.Credentials correct, returns the instance of user
     */
    public User getUser(final Credentials credentials) {
        String name = credentials.getName();
        if (database.containsKey(name)) {
            User user = database.get(name);
            if (user.getCredentials().getPassword().compareTo(credentials.getPassword()) == 0) {
                return user;
            }
        }
        return null;
    }

}
