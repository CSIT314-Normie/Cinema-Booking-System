package Main.Controller.Manager;

import Main.Entity.*;

import java.util.*;

public class UpdateMovieInfoController {
    private final Movie movie = new Movie();

    public UpdateMovieInfoController() {}

    public boolean updateMovieInfo(ArrayList<String> movieInfo, String movieName) {
        return movie.updateMovieInfo(movieInfo, movieName);
    } 
}
