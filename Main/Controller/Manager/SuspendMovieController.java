package Main.Controller.Manager;

import Main.Entity.*;

/**
 * This controller handles the suspension movies - Done by the CINEMA MANAGER
 */

public class SuspendMovieController {

    private final Movie movie = new Movie();
    private String email;

    public SuspendMovieController() {
    }

    public boolean suspendMovie(String movieName) {
        return this.movie.suspendMovie(movieName);
    }
}
