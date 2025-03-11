package com.redhat.demo;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import java.io.IOException;

@Path("/read")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class BlogReaderResource {

    private static final Logger LOGGER = Logger.getLogger(BlogReaderResource.class);

    @Inject
    BlogReaderService blogReaderService;

    @GET
    public String read(@QueryParam("url") String url) {
        try {
            LOGGER.info("🔍 Summarizing: " + url);
            return blogReaderService.summarize(url);
        } catch (IOException e) {
            LOGGER.error("Error processing URL", e);
            throw new WebApplicationException("Failed to fetch content", 500);
        }
    }
}