package de.fips.imagehaiku.resources;

import com.codahale.metrics.annotation.Timed;
import de.fips.imagehaiku.domain.Haiku;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/haiku")
@Produces(MediaType.APPLICATION_JSON)
public class HaikuResource {

    @GET
    @Timed
    public Haiku randomHaiku() {
        return Haiku.create("Basho Matsuo", "An old silent pond...\nA frog jumps into the pond,\nsplash! Silence again.", "img001.png");
    }
}
