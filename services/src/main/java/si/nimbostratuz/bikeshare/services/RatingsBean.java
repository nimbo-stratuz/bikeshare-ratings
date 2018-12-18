package si.nimbostratuz.bikeshare.services;

import lombok.extern.java.Log;
import si.nimbostratuz.bikeshare.models.entities.Rating;

import javax.enterprise.context.ApplicationScoped;

@Log
@ApplicationScoped
public class RatingsBean extends EntityBean<Rating> {

    @Override
    public Rating get(Integer ratingId) {
        // TODO - get
        Rating rating = new Rating();
        return rating;
    }


    @Override
    public Rating create(Rating rating) {
        // TODO - create
        return rating;
    }

    @Override
    public Rating update(Integer ratingId, Rating rating) {
        // TODO - update
        return rating;
    }

    @Override
    public void delete(Integer ratingId) {
        // TODO - delete
    }


}
