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
@Path("rentals")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RatingsResource {

    @Inject
    private RatingsBean ratingsBean;
    @Context
    protected UriInfo uriInfo;

    @GET
    @Path("{id}")
    public Response getRental(@PathParam("id") Integer id) {

        return Response.ok(ratingsBean.get(id)).build();
    }

}
