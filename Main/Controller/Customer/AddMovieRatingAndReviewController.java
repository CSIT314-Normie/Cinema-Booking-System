package Main.Controller.Customer;

import java.util.*;

import Main.Entity.Reviews;

public class AddMovieRatingAndReviewController {

    Reviews reviews = new Reviews();

    public AddMovieRatingAndReviewController() {
    }

    /**
     * Add a review and rating for a movie
     * @param ArrayList<String> reviewInfo
     * @return boolean true if success, false if fail
     */
    public boolean addReviewRating(ArrayList<String> reviewInfo) {
        return reviews.addReviewRating(reviewInfo);
    }
    
}
