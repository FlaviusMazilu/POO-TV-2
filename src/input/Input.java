package input;

import java.util.ArrayList;

public final class Input {
    private ArrayList<ActionsInput> actions;
    private ArrayList<UsersInput> users;
    private ArrayList<MovieInput> movies;

    public ArrayList<ActionsInput> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<ActionsInput> actions) {
        this.actions = actions;
    }

    public ArrayList<UsersInput> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<UsersInput> users) {
        this.users = users;
    }

    public ArrayList<MovieInput> getMovies() {
        return movies;
    }

    public void setMovie(final ArrayList<MovieInput> movies) {
        this.movies = movies;
    }

}
