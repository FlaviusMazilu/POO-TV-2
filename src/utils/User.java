package utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;

public final class User implements Observer {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies;
    private ArrayList<Movie> purchasedMovies;
    private ArrayList<Movie> watchedMovies;
    private ArrayList<Movie> likedMovies;
    private ArrayList<Movie> ratedMovies;
    @JsonIgnore
    private HashMap<String, Double> ratings;

    private ArrayList<Notification> notifications;
    @JsonIgnore
    private ArrayList<String> subscribedGenres;

    public void addSubscription(String genre) {
        subscribedGenres.add(genre);
    }
    @Override
    public void update(Notification notification) {
        notifications.add(notification);
        //TODO update pentru deleteMovie ++tokens/freeMovie
        //TODO make default action pentru fiecare pagina
    }
    public User(final User user) {
        this.credentials = new Credentials(user.credentials);
        this.likedMovies = deepCopyList(user.getLikedMovies());
        this.purchasedMovies = deepCopyList(user.purchasedMovies);
        this.ratedMovies = deepCopyList(user.ratedMovies);
        this.numFreePremiumMovies = user.numFreePremiumMovies;
        this.watchedMovies = deepCopyList(user.watchedMovies);
        this.tokensCount = user.tokensCount;
        this.ratings = user.ratings;
        this.subscribedGenres = new ArrayList<>(user.getSubscribedGenres());
        this.notifications = deepCopyNotif(user.getNotifications());
    }

    private ArrayList<Notification> deepCopyNotif(final ArrayList<Notification> notifs) {
        ArrayList<Notification> newList = new ArrayList<>();
        for (Notification notification : notifs) {
            newList.add(new Notification(notification));
        }
        return newList;
    }
    private ArrayList<Movie> deepCopyList(final ArrayList<Movie> oldList) {
        ArrayList<Movie> newList = new ArrayList<>();
        for (Movie movie : oldList) {
            newList.add(new Movie(movie));
        }
        return newList;
    }

    public User(final Credentials credentials) {
        this.credentials = new Credentials(credentials);
        purchasedMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        notifications = new ArrayList<>();
        subscribedGenres = new ArrayList<>();
        ratings = new HashMap<>();
        numFreePremiumMovies = Cons.NR_FREE_MOVIES;
    }

    /**
     *
     * @param movieName The name of the movie to be tested if watched
     * @return true if user has watched the movie false if not
     */
    public boolean userHasWatched(final String movieName) {
        for (Movie movie : watchedMovies) {
            if (movie.getName().equals(movieName)) {
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param movieName The name of the movie to be tested if purchased
     * @return true if user has purchased the movie false if not
     */
    public boolean userHasPurchased(final String movieName) {
        for (Movie movie : purchasedMovies) {
            if (movie.getName().equals(movieName)) {
                return true;
            }
        }
        return false;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
    }

    public void setSubscribedGenres(ArrayList<String> subscribedGenres) {
        this.subscribedGenres = subscribedGenres;
    }

    public HashMap<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(HashMap<String, Double> ratings) {
        this.ratings = ratings;
    }
}