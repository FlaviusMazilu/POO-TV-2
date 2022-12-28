package pages;

import input.*;
import utils.Movie;
import utils.User;
import utils.OutputCreater;

import databases.UserDataBase;
import databases.MoviesDataBase;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public final class MoviesPage extends Page {
    private HashMap<String, Page> pages;
    private static MoviesPage instance;
    private ArrayList<Movie> movies;

    public void setMovies(final ArrayList<Movie> newMovies) {
        movies = newMovies;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    private MoviesPage() {
        pages = new HashMap<>();
        movies = new ArrayList<>();
    }

    /**
     * Singleton Pattern for creating only one instance of the Pages.MoviesPage class
     * @return returns the only once instance created
     */
    public static MoviesPage getInstance() {
        if (instance == null) {
            instance = new MoviesPage();
        }
        return instance;
    }

    /**
     * Refills the movies with all the movies available for the user country
     */
    public void getAllMovies() {
        movies = new ArrayList<>();
        User user = Authenticated.getInstance().getUser();
        for (Movie movie : MoviesDataBase.getInstance().getMoviesList()) {
            int ok = 1;
            for (String countryBanned : movie.getCountriesBanned()) {
                if (countryBanned.compareTo(user.getCredentials().getCountry()) == 0) {
                    ok = 0;
                    break;
                }
            }
            if (ok == 1) {
                movies.add(movie);
            }
        }
    }
    @Override
    public Page changePage(final ActionsInput action) {
        if (!pages.containsKey(action.getPage())) {
            if (action.getPage().equals("movies")) {
                return this;
            }
            OutputCreater.addObject("Error", null, null);
            return this;
        } else {
            // if the page that is changed is a SeeDetails page
            // it should be printed the movie selected for "see details"
            Page page = pages.get(action.getPage());
            if (page.getClass() == SeeDetails.class) {
                String movieName = action.getMovie();

                for (Movie movie : movies) {
                    if (movie.getName().equals(movieName)) {
                        SeeDetails.getInstance().setMovie(movie);
                        ArrayList<Movie> moviesOutput = new ArrayList<>();
                        moviesOutput.add(movie);
                        OutputCreater.addObject(null, moviesOutput,
                                        Authenticated.getInstance().getUser());
                        return pages.get(action.getPage());
                    }
                }
                // if movie not found
                OutputCreater.addObject("Error", null, null);
                return this;
            }
            return pages.get(action.getPage());
        }
    }
    private void filterByActors(final ContainsInput containsInput) {
        ArrayList<String> actors = containsInput.getActors();
        if (actors == null) {
            return;
        }
        int i = 0;
        while (i < movies.size()) {
            Movie movie = movies.get(i);
            int ok2 = 1;
            for (String actorContained : actors) {
                int ok = 0;
                for (String actor : movie.getActors()) {
                    if (actor.equals(actorContained)) {
                        ok = 1;
                        break;
                    }
                }
                if (ok == 0) {
                    ok2 = 0;
                    break;
                }
            }
            if (ok2 == 0) {
                movies.remove(movie);
            } else {
                i++;
            }
        }
    }

    private void filterByGenre(final ContainsInput containsInput) {
        ArrayList<String> genres = containsInput.getGenre();
        if (genres == null) {
            return;
        }
        int i = 0;
        while (i < movies.size()) {
            Movie movie = movies.get(i);
            int ok2 = 1;
            for (String genreContained : genres) {
                int ok = 0;
                for (String genre: movie.getGenres()) {
                    if (genreContained.equals(genre)) {
                        ok = 1;
                        break;
                    }
                }
                if (ok == 0) {
                    ok2 = 0;
                    break;
                }
            }
            if (ok2 == 0) {
                movies.remove(movie);
            } else {
                i++;
            }
        }
    }
    private void filter(final FiltersInput filtersInput) {
        getAllMovies();
        SortInput sortInput = filtersInput.getSort();
        ContainsInput containsInput = filtersInput.getContains();
        if (containsInput != null) {
            filterByActors(containsInput);
            filterByGenre(containsInput);
        }

        if (sortInput == null) {
            OutputCreater.addObject(null, movies, Authenticated.getInstance().getUser());
            return;
        }
        String duration = sortInput.getDuration();
        String rating = sortInput.getRating();
        movies.sort(new Comparator<Movie>() {
        @Override
        public int compare(final Movie o1, final Movie o2) {
            if (duration != null && duration.equals("decreasing")) {
                if (o1.getDuration() > o2.getDuration()) {
                    return -1;
                }
                if (o1.getDuration() < o2.getDuration()) {
                    return 1;
                }
            }
            if (duration != null && duration.equals("increasing")) {
                if (o1.getDuration() > o2.getDuration()) {
                    return 1;
                }
                if (o1.getDuration() < o2.getDuration()) {
                    return -1;
                }
            }
            if (rating != null && rating.equals("decreasing")) {
                return Double.compare(o2.getRating(), o1.getRating());
            }
            if (rating != null && rating.equals("increasing")) {
                return Double.compare(o1.getRating(), o2.getRating());
            }
            return 0;
        }
        });
        OutputCreater.addObject(null, movies, Authenticated.getInstance().getUser());


    }
    private void search(final String start) {
        ArrayList<Movie> newList = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.getName().startsWith(start)) {
                newList.add(movie);
            }
        }
        movies = newList;
        OutputCreater.addObject(null, newList, Authenticated.getInstance().getUser());
    }
    @Override
    public Page onPage(final ActionsInput action, final UserDataBase userDB) {
        switch (action.getFeature()) {
            case "search" -> search(action.getStartsWith());
            case "filter" -> filter(action.getFilters());
            default -> OutputCreater.addObject("Error", null, null);
        }
        return this;
    }

    @Override
    public void setPages() {
        pages.put("homepage autentificat", Authenticated.getInstance());
        pages.put("see details", SeeDetails.getInstance());
        pages.put("logout", LogoutPage.getInstance());
    }

}
