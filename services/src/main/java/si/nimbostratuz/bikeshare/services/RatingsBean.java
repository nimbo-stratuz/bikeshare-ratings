package si.nimbostratuz.bikeshare.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import lombok.extern.java.Log;
import si.nimbostratuz.bikeshare.models.entities.Rating;

import javax.enterprise.context.ApplicationScoped;
import javax.management.Query;
import java.util.List;

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


    public List<Rating> getAll(QueryParameters query) {
        List<Rating> ratings  =JPAUtils.queryEntities(em, Rating.class, query);

        return ratings;
    }

}
