package observer;

import utils.Movie;

public interface Observable {
    void notifyObservers(Movie movie, String feature);
}
