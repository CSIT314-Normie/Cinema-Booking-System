package Main.Controller.Customer;

import java.util.ArrayList;

import Main.Entity.Reviews;

public class GetMovieReviewsController {
    Reviews review = new Reviews();

    public GetMovieReviewsController() { 
    }

    public ArrayList<String> getMovieReviews(String movieName) { 
        return review.getAllReviewsRatingsForMovie(movieName);
    }
}
