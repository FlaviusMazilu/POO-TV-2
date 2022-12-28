package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public abstract class OutputCreater {
    /**
     *
     * @param error error that should be printed eg "Error"/null
     * @param movies list of currentMovies
     * @param user current Utils.User
     */
    public static void addObject(final String error, final ArrayList<Movie> movies,
                                 final User user) {
        ObjectNode objectNode = IO.objectMapper.createObjectNode();
        objectNode.put("error", error);
        if (movies == null) {
            ArrayList<Movie> aux = new ArrayList<>();
            objectNode.putPOJO("currentMoviesList", aux);

        } else {
              objectNode.putPOJO("currentMoviesList", movies);
              ArrayList<Movie> list = new ArrayList<>();
              for (Movie movie : movies) {
                  list.add(new Movie(movie));
              }
            objectNode.putPOJO("currentMoviesList", list);

        }
        if (user == null) {
            objectNode.put("currentUser", (JsonNode) null);
        } else {
            objectNode.putPOJO("currentUser", new User(user));
        }
        IO.output.add((objectNode));
    }
}
