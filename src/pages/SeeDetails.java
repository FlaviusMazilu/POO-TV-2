package pages;

import input.ActionsInput;

import java.util.ArrayList;
import java.util.HashMap;
import utils.User;
import utils.Movie;
import utils.Cons;
import utils.OutputCreater;
import databases.UserDataBase;

public class SeeDetails extends Page {

    private static SeeDetails instance;
    private HashMap<String, Page> pages;

    private Movie movie;

    @Override
    public void subscribeAction(String subscribedGenre) {
        User user = Authenticated.getInstance().getUser();
        for (String subscription : user.getSubscribedGenres()) {
            if (subscription.equals(subscribedGenre)) {
                OutputCreater.addObject("Subscribed already", null, null);
                return;
            }
        }
        for (String genre : movie.getGenres()) {
            if (genre.equals(subscribedGenre)) {
                user.addSubscription(genre);
                return;
            }
        }
        OutputCreater.addObject("Genre not found", null, null);
    }
    private SeeDetails() {
        pages = new HashMap<>();
    }

    public static SeeDetails getInstance() {
        if (instance == null) {
            instance = new SeeDetails();

        }
        return instance;
    }
    @Override
    public Page changePage(final ActionsInput action) {
        if (!pages.containsKey(action.getPage())) {
            OutputCreater.addObject("Error", null, null);
            return this;
        } else {
            return pages.get(action.getPage());
        }
    }
    private void purchase(final ActionsInput action) {
        User user = Authenticated.getInstance().getUser();
        int tokens = user.getTokensCount();
        String accountType = user.getCredentials().getAccountType();

        for (Movie moviePurchased : user.getPurchasedMovies()) {
            if (moviePurchased.getName().equals(movie.getName())) {
                OutputCreater.addObject("Already purchased", null, null);
                return;
            }
        }
        if (accountType.equals("premium") && user.getNumFreePremiumMovies() > 0) {
            user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() - 1);
        } else {
            if (tokens < Cons.PRICE_MOVIE) {
                OutputCreater.addObject("Error", null, null);
                return;
            }
            user.setTokensCount(tokens - Cons.PRICE_MOVIE);
        }

        user.getPurchasedMovies().add(movie);

        ArrayList<Movie> listOutput = new ArrayList<>();
        listOutput.add(movie);
        OutputCreater.addObject(null, listOutput, user);
    }

    private void watch(final ActionsInput action) {
        User user = Authenticated.getInstance().getUser();
        if (!user.userHasPurchased(movie.getName())) {
            OutputCreater.addObject("Error", null, null);
            return;
        }
        if (user.userHasWatched(movie.getName())) {
            return;
        }
        user.getWatchedMovies().add(movie);

        ArrayList<Movie> listOutput = new ArrayList<>();
        listOutput.add(movie);
        OutputCreater.addObject(null, listOutput, user);

    }
    private void like(final ActionsInput action) {
        User user = Authenticated.getInstance().getUser();
        if (!user.userHasWatched(movie.getName())) {
            OutputCreater.addObject("Error", null, null);
            return;
        }
        if (user.userhasLiked(movie.getName())) {
            return;
        }
        user.getLikedMovies().add(movie);
        movie.likeOp();

        ArrayList<Movie> listOutput = new ArrayList<>();
        listOutput.add(movie);
        OutputCreater.addObject(null, listOutput, user);
    }

    private void rate(final ActionsInput actions) {
        User user = Authenticated.getInstance().getUser();
        if (!user.userHasWatched(movie.getName())) {
            OutputCreater.addObject("Error", null, null);
            return;
        }
        if (actions.getRate() > Cons.MAX_RATE) {
            OutputCreater.addObject("Error", null, null);
            return;
        }
        movie.rateOp(actions.getRate(), user);
        ArrayList<Movie> listOutput = new ArrayList<>();
        listOutput.add(movie);
        OutputCreater.addObject(null, listOutput, user);
    }

    @Override
    public Page onPage(final ActionsInput action, final UserDataBase userDB) {
        switch (action.getFeature()) {
            case "purchase" -> purchase(action);
            case "watch" -> watch(action);
            case "like" -> like(action);
            case "rate" -> rate(action);
            default -> OutputCreater.addObject("Error", null, null);
        }
        return this;
    }

    @Override
    public void setPages() {
        pages.put("homepage autentificat", Authenticated.getInstance());
        pages.put("movies", MoviesPage.getInstance());
        pages.put("upgrades", Upgrades.getInstance());
        pages.put("logout", LogoutPage.getInstance());
    }
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(final Movie movie) {
        this.movie = movie;
    }


}
