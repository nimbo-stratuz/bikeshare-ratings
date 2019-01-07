package si.nimbostratuz.bikeshare.services;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import lombok.extern.java.Log;
import si.nimbostratuz.bikeshare.models.dtos.BicycleDTO;
import si.nimbostratuz.bikeshare.models.entities.Rating;
import si.nimbostratuz.bikeshare.services.configuration.AppProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Log
@ApplicationScoped
public class RatingsBean extends EntityBean<Rating> {
    @Inject
    private AppProperties appProperties;
    @Inject
    @DiscoverService("bikeshare-payments")
    private WebTarget paymentsWebTarget;
    @Inject
    @DiscoverService("bikeshare-catalogue")
    private WebTarget catalogueWebTarget;


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


    private void bonusForHighRating(Rating rating) {
        log.info("Bonus for high rating");
        if (rating.getRating() >= 4) {
            log.info("Bonus for high rating - TRUE");

            // Retrieve the targeted Bicycle. We need it to get the Owner Id.
            BicycleDTO targetedBicycle = catalogueWebTarget.path("v1")
                    .path("bicycles").path(Integer.toString(rating.getBicycleId()))
                    .request().get().readEntity(BicycleDTO.class);
            log.info("Bonus for high rating - Got target bicycle.");
            try {
                // Pay the bonus to the owner
                paymentsWebTarget.path("v1").path("users").path("give-bonus")
                        .path(Integer.toString(targetedBicycle.getOwnerId())).request()
                        .post(Entity.entity(appProperties.getBonusForHighRatingNumber(), MediaType.APPLICATION_JSON));
                log.info("Gave bonus to user.");
            } catch (Exception e) {
                log.info(Rating.class.getName()+ e + "Failed to pay bonus to user");
            }
        }

    }
    @Override
    public Rating create(Rating rating) {
       try {
           beginTx();
           bonusForHighRating(rating);
           em.persist(rating);
           commitTx();
       } catch (Exception e) {
           rollbackTx();
           log.info("Failed to create a rating."+ e);
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
