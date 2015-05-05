package de.fips.imagehaiku.resources;

import com.codahale.metrics.annotation.Timed;
import de.fips.common.lang.Images;
import de.fips.imagehaiku.domain.Image;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/image")
public class ImageResource {

    @GET
    @Timed
    @Path("{imageId}")
    public Response download(@PathParam("imageId") String filename) {
        return Optional.of(Image.create("img001.png", "image/png", Images.createExampleImage("PNG", 256, 256, new java.awt.Color(153, 217, 234))))
                .map(image -> Response.ok(image.getData(), image.getMediaType()).build())
                .orElseThrow(Responses::notFound);
    }
}
