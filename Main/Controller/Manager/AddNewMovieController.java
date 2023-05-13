package Main.Controller.Manager;

import Main.Entity.*;

import java.util.*;

public class AddNewMovieController {
    private final Movie movie = new Movie();
    
    public AddNewMovieController() {}

    public boolean insertMovie(ArrayList<String> movieInfo) {
        return movie.insertMovie(movieInfo);
    }
}
