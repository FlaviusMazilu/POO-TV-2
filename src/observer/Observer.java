package observer;

import utils.Movie;
import utils.Notification;

public interface Observer {
    void update(Notification notification, Movie movie);
}
