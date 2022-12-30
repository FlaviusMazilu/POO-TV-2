package utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import input.MovieInput;

import java.util.ArrayList;

public final class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;


    private int numLikes;
    private int numRatings;
    @JsonIgnore
    private double sumRates;
    private double rating;

    @JsonIgnore
    private double individualRating;

    public Movie() {

    }

    public Movie(final Movie movie) {
        this.duration = movie.duration;
        this.genres = movie.genres;
        this.actors = movie.actors;
        this.countriesBanned = movie.countriesBanned;
        this.year = movie.year;
        this.sumRates = movie.sumRates;
        this.numRatings = movie.numRatings;
        this.numLikes = movie.numLikes;
        this.name = movie.name;
        this.rating = movie.rating;
        this.individualRating = movie.individualRating;
    }

    public Movie(final MovieInput movieInput) {
        this.name = movieInput.getName();
        this.year = movieInput.getYear();
        this.duration = movieInput.getDuration();
        this.genres = movieInput.getGenres();
        this.actors = movieInput.getActors();
        this.countriesBanned = movieInput.getCountriesBanned();
    }

    /**
     * Method to increase the number of likes of this movie
     */
    public void likeOp() {
        numLikes++;
    }

    /**
     * Method to find if the movie is part of a genre
     * @param genre: name of the genre
     * @return true if this movie is part of the genre passed as argument
     */
    public boolean hasGenre(final String genre) {
        for (String genreMovie : getGenres()) {
            if (genre.equals(genreMovie)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to rate this movie
     * @param rate: the rating accorded
     * @param user: who gave the rating
     */
    public void rateOp(final double rate, final User user) {
        if (user.getRatings().containsKey(name)) {
            double ratingAcc = user.getRatings().get(name);
            sumRates -= ratingAcc;
            sumRates += rate;
            if (numRatings > 0) {
                rating = sumRates / numRatings;
            }
        } else {
            numRatings++;
            sumRates += rate;
            if (numRatings > 0) {
                rating = sumRates / numRatings;
            }
            user.getRatings().put(name, rate);
            user.getRatedMovies().add(this);
        }
    }


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getYear() {
        return Integer.toString(year);
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int likes) {
        this.numLikes = likes;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int nrRates) {
        this.numRatings = nrRates;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

}
