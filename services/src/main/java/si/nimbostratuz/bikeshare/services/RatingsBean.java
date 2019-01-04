package si.nimbostratuz.bikeshare.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import lombok.extern.java.Log;
import si.nimbostratuz.bikeshare.models.entities.Rating;
import si.nimbostratuz.bikeshare.services.configuration.AppProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
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
       try {
           beginTx();
           em.persist(rating);
           commitTx();
       } catch (Exception e) {
           rollbackTx();
           log.throwing(Rating.class.getName(), "create", e);
           throw new BadRequestException();
       }
       return rating;
    }

    @Override
    public Rating update(Integer ratingId, Rating rating) {
        Rating originalRating = this.get(ratingId);

        try {
            beginTx();
            rating.setId(originalRating.getId());
            rating = em.merge(rating);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
            log.throwing(Rating.class.getName(), "update", e);
            throw new BadRequestException();
        }

        return rating;
    }

    @Override
    public void delete(Integer ratingId) {
        try {
            beginTx();
            Rating rating = this.get(ratingId);
            em.remove(rating);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
            log.throwing(Rating.class.getName(), "delete", e);
            throw new BadRequestException();
        }
    }


    public List<Rating> getAll(QueryParameters query) {
        List<Rating> ratings  = JPAUtils.queryEntities(em, Rating.class, query);

        return ratings;
    }

}
