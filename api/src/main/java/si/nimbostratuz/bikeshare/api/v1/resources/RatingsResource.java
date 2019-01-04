package si.nimbostratuz.bikeshare.api.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.nimbostratuz.bikeshare.models.entities.Rating;
import si.nimbostratuz.bikeshare.services.RatingsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("ratings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RatingsResource {

    @Inject
    private RatingsBean ratingsBean;
    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getAll() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Rating> rentals = ratingsBean.getAll(query);

        return Response.ok(rentals).build();
    }

    @GET
    @Path("{id}")
    public Response getRating(@PathParam("id") Integer id) {

        return Response.ok(ratingsBean.get(id)).build();
    }

    @GET
    @Path("bicycle/{id}")
    public Response getBikeRatings(@PathParam("id") Integer id) {

        return Response.ok(ratingsBean.getAllBikeRatings(id)).build();
    }

    @POST
    public Response createRating(Rating rating) {
        return Response.ok(ratingsBean.create(rating)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateRating(@PathParam("id") Integer id, Rating rating) {
        return Response.ok(ratingsBean.update(id, rating)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteRating(@PathParam("id") Integer id) {
        ratingsBean.delete(id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
