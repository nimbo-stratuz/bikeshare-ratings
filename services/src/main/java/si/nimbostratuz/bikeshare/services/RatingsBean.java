package si.nimbostratuz.bikeshare.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import lombok.extern.java.Log;
import si.nimbostratuz.bikeshare.models.entities.Rating;
import si.nimbostratuz.bikeshare.services.configuration.AppProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@Log
@ApplicationScoped
public class RatingsBean extends EntityBean<Rating> {
    @Inject
    private AppProperties appProperties;

    @Override
    public Rating get(Integer ratingId) {
        Rating rating = em.find(Rating.class, ratingId);
        return rating;
    }

    // All ratings for a specific Bicycle.
    public List<Rating> getAllBikeRatings(Integer bicycle_id) {
        List<Rating> ratings = em.createQuery(
                "SELECT r FROM rating r WHERE r.bicycleId = :bicycle_id")
                .setParameter("bicycle_id", bicycle_id)
                .getResultList();

        return ratings;
    }


    @Override
    public Rating create(Rating rating) {
        //Rating  = new Rating();
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
        List<Rating> ratings  = JPAUtils.queryEntities(em, Rating.class, query);

        return ratings;
    }

}
