package databases;

import input.ActionsInput;
import utils.*;
import databases.MoviesDataBase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public final class MoviesDataBase implements Observable {
    private final LinkedHashMap<String, Movie> movies;
    private static MoviesDataBase instance;
    private UserDataBase userDB;
    public void addUsers(UserDataBase userDB) {
        this.userDB = userDB;
    }

    private MoviesDataBase() {
        movies = new LinkedHashMap<>();
    }
    /**
     * Singleton Pattern for creating only one instance of the DBs.MoviesDataBase class
     * @return instance returns the only once instance created
     */
    public static MoviesDataBase getInstance() {
        if (instance == null) {
            instance = new MoviesDataBase();
        }
        return instance;
    }

    /**
     * Used to create a new instance of DBs.MoviesDataBase and flush the other existing one
     */
    public static void setInstanceNull() {
        instance = null;
    }

    /**
     * The method adds a movie to the database in case the movie name doesn't already exists
     * @param movie the movie that should be added to the database
     */
    public void addMovie(final Movie movie) {
        movies.put(movie.getName(), movie);
    }

    /**
     *
     * @param name is the name of the movie that should be returned
     * @return the movie with the specified name
     */
    public Movie getMovie(final String name) {
        return movies.get(name);
    }


    /**
     * Regardless of the implementation of the database, it creates a list of all the movies
     * in the right order they were added
     * @return array of all the movies in the database
     */
    public ArrayList<Movie> getMoviesList() {
        ArrayList<Movie> list = new ArrayList<>();
        for (Map.Entry<String, Movie> entry : movies.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public void addDeleteMovie(ActionsInput action) {
        String feature = action.getFeature();
        Movie movie;
        if (feature.equals("add")) {
            movie = new Movie(action.getAddedMovie());
        } else {
            movie = new Movie();
            movie.setName(action.getDeletedMovie());
        }

        if (feature.equals("add")) {
            if (movies.containsKey(movie.getName())) {
                OutputCreater.addObject("Movie already exists", null, null);
                return;
            }
            addMovie(movie);
            notifyObservers(movie, feature);
        } else {
            String movieName = movie.getName();
            if (movies.remove(movieName) == null) {
                OutputCreater.addObject("delete movie error", null, null);
                return;
            }
            notifyObservers(movie, feature);
        }
    }

    @Override
    public void notifyObservers(Movie movie, String feature) {
        Notification notification;
        if (feature.equals("add")) {
            notification = new Notification(movie.getName(), "ADD");
        } else {
            notification = new Notification(movie.getName(), "DELETE");
        }

        for (Map.Entry<String, User> entry : userDB.getDatabase().entrySet()) {
            User user = entry.getValue();

            for (String subscribedGenre : user.getSubscribedGenres()) {
                int ok = 0;
                for (String movieGenre: movie.getGenres()) {
                    if (movieGenre.equals(subscribedGenre)) {
                        user.update(notification);
                        ok = 1;
                        break;
                    }
                }
                if (ok == 1) {
                    break;
                }
            }
        }
    }
}
