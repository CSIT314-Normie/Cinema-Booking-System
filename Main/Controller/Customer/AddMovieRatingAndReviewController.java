package Main.Controller.Customer;

import java.util.*;

import Main.Entity.ManageReviews;

public class AddMovieRatingAndReviewController {

    ManageReviews reviews = new ManageReviews();

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
