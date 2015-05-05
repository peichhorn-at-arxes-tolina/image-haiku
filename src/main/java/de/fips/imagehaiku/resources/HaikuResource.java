package de.fips.imagehaiku.resources;

import com.codahale.metrics.annotation.Timed;
import de.fips.imagehaiku.domain.Haiku;
import de.fips.imagehaiku.domain.HaikuRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/haiku")
@Produces(MediaType.APPLICATION_JSON)
public class HaikuResource {
    private final HaikuRepository repository;

    public HaikuResource(HaikuRepository repository) {
        this.repository = repository;
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Haiku create(Haiku haiku) {
        return repository.save(Haiku.create(haiku)).orElseThrow(Responses::notFound);
    }

    @GET
    @Timed
    @Path("{haikuId}")
    public Haiku haikuById(@PathParam("haikuId") String haikuId) {
        return repository.haikuById(haikuId).orElseThrow(Responses::notFound);
    }

    /**
     * @implNote The pseude-random document access in this implementation does not hava a uniform distribution.
     */
    @GET
    @Timed
    public Haiku randomHaiku() {
        final double random = Math.random();
        return repository.haikuByRandomGte(random).orElseGet(() -> repository.haikuByRandomLt(random).orElseThrow(Responses::notFound));
    }
}
