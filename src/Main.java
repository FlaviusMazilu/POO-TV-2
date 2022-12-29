import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import databases.MoviesDataBase;
import databases.UserDataBase;
import pages.*;
import utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import input.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    /**
     * Method adds the users from the input to the database
     * @param userDB populated instance of DBs.UserDataBase
     */
    public static void populateUsers(final UserDataBase userDB) {
        for (UsersInput userInput: IO.inputData.getUsers()) {
            CredentialsInput credentialsInput = userInput.getCredentials();
            Credentials credentials1 = new Credentials(credentialsInput);
            User user = new User(credentials1);
            userDB.addUser(user);
        }
    }

    /**
     * Method adds the movies from the input to the database
     * @param moviesDB populated instance of DBs.MoviesDataBase
     */
    public static void populateMovies(final MoviesDataBase moviesDB) {
        for (MovieInput movieInput : IO.inputData.getMovies()) {
            Movie movie = new Movie(movieInput);
            moviesDB.addMovie(movie);
        }
    }

    private static void createPages() {
        ArrayList<Page> pages = new ArrayList<>();

        pages.add(NotAuthenticated.getInstance());
        pages.add(LoginPage.getInstance());
        pages.add(RegisterPage.getInstance());
        pages.add(Authenticated.getInstance());
        pages.add(MoviesPage.getInstance());
        pages.add(Upgrades.getInstance());
        pages.add(SeeDetails.getInstance());
        pages.add(LogoutPage.getInstance());

        for (Page page : pages) {
            page.setPages();
        }
    }
    public static void main(String[] args) throws IOException {

        Page page = NotAuthenticated.getInstance();
        final UserDataBase userDB = new UserDataBase();
        final MoviesDataBase moviesDB = MoviesDataBase.getInstance();

        IO.objectMapper = new ObjectMapper();
        IO.inputData = IO.objectMapper.readValue(new File(args[0]), Input.class);
        IO.output = IO.objectMapper.createArrayNode();

        populateUsers(userDB);
        populateMovies(moviesDB);
        moviesDB.addUsers(userDB);
        createPages();
        Invoker invoker = new Invoker(userDB, moviesDB);

        for (ActionsInput action : IO.inputData.getActions()) {
            invoker.execute(action);
        }
        invoker.getRecommandation();

        MoviesPage.getInstance().setMovies(new ArrayList<Movie>());
        Authenticated.getInstance().setUser(null);
        MoviesDataBase.setInstanceNull();

        ObjectWriter objectWriter = IO.objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), IO.output);

        char[] inputPath = args[0].toCharArray();
        String outputPath = "C:\\Users\\mazil\\IdeaProjects\\proiect2\\checker\\resources\\out\\"
                + args[1] + inputPath[inputPath.length - 6] + ".json";
        objectWriter.writeValue(new File(outputPath), IO.output);
    }
}
