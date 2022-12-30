package pages;

import input.ActionsInput;

import java.util.HashMap;
import utils.User;
import utils.Movie;
import utils.Cons;
import utils.OutputCreater;
import databases.UserDataBase;

public class SeeDetails extends Page {

    private static SeeDetails instance;
    private final HashMap<String, Page> pages;

    private Movie movie;

    @Override
    public void subscribeAction(final ActionsInput actionsInput) {
        String subscribedGenre = actionsInput.getSubscribedGenre();
        User user = Authenticated.getInstance().getUser();
        for (String subscription : user.getSubscribedGenres()) {
            if (subscription.equals(subscribedGenre)) {
                OutputCreater.addObject("Error", null, null);
                return;
            }
        }
        for (String genre : movie.getGenres()) {
            if (genre.equals(subscribedGenre)) {
                user.addSubscription(genre);
                return;
            }
        }
        OutputCreater.addObject("Error", null, null);
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

    private void purchase() {
        User user = Authenticated.getInstance().getUser();
        int tokens = user.getTokensCount();
        String accountType = user.getCredentials().getAccountType();

        for (Movie moviePurchased : user.getPurchasedMovies()) {
            if (moviePurchased.getName().equals(movie.getName())) {
                OutputCreater.addObject("Error", null, null);
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
        OutputCreater.addObject(movie, user);
    }

    private void watch() {
        User user = Authenticated.getInstance().getUser();
        if (!user.userHasPurchased(movie.getName())) {
            OutputCreater.addObject("Error", null, null);
            return;
        }
        if (user.userHasWatched(movie.getName())) {
            return;
        }
        user.getWatchedMovies().add(movie);
        OutputCreater.addObject(movie, user);

    }

    private void like() {
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

        OutputCreater.addObject(movie, user);
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
        OutputCreater.addObject(movie, user);
    }

    @Override
    public Page onPage(final ActionsInput action, final UserDataBase userDB) {
        switch (action.getFeature()) {
            case "purchase" -> purchase();
            case "watch" -> watch();
            case "like" -> like();
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
