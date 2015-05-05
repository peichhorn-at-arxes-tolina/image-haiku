package de.fips.imagehaiku.resources;

import com.codahale.metrics.annotation.Timed;
import de.fips.imagehaiku.domain.ImageRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/image")
public class ImageResource {
    private final ImageRepository repository;

    public ImageResource(ImageRepository repository) {
        this.repository = repository;
    }

    @GET
    @Timed
    @Path("{imageId}")
    public Response download(@PathParam("imageId") String filename) {
        return repository.imageByFilename(filename)
                .map(image -> Response.ok(image.getData(), image.getMediaType()).build())
                .orElseThrow(Responses::notFound);
    }
}
