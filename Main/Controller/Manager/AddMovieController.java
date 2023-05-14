package Main.Controller.Manager;

import Main.Entity.*;

import java.util.*;

public class AddMovieController {
    Movie movie = new Movie();
    
    public AddMovieController() {}

    public boolean insertMovie(ArrayList<String> movieInfo) {
        return movie.insertMovie(movieInfo);
    }
}
