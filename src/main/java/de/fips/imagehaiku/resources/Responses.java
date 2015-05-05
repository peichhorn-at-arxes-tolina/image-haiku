package de.fips.imagehaiku.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public final class Responses {

    private Responses() {
    }

    public static WebApplicationException notFound() {
        return new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }
}
