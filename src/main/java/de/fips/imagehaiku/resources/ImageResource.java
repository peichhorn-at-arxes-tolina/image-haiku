package de.fips.imagehaiku.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.io.ByteStreams;
import de.fips.imagehaiku.domain.Image;
import de.fips.imagehaiku.domain.ImageRepository;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Optional;

@Path("/image")
public class ImageResource {
    private final ImageRepository repository;

    public ImageResource(ImageRepository repository) {
        this.repository = repository;
    }

    @POST
    @Timed
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(
            @FormDataParam("file") final InputStream stream,
            @FormDataParam("file") final FormDataContentDisposition contentDispositionHeader
    ) {
        createImage(stream, contentDispositionHeader)
                .filter(this::imageMediatype)
                .map(repository::save)
                .orElseThrow(Responses::notFound);
        return Response.ok().build();
    }

    @GET
    @Timed
    @Path("{imageId}")
    public Response download(@PathParam("imageId") String filename) {
        return repository.imageByFilename(filename)
                .map(image -> Response.ok(image.getData(), image.getMediaType()).build())
                .orElseThrow(Responses::notFound);
    }

    private boolean imageMediatype(final Image image) {
        final String mediatype = image.getMediaType();
        return mediatype != null && mediatype.matches("image/.*");
    }

    private Optional<Image> createImage(final InputStream stream, final FormDataContentDisposition contentDispositionHeader) {
        try {
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);
            final String filename = contentDispositionHeader.getFileName();
            final String mediatype = URLConnection.guessContentTypeFromStream(bufferedInputStream);
            final byte[] data = ByteStreams.toByteArray(bufferedInputStream);
            return Optional.of(Image.create(filename, mediatype, data));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
