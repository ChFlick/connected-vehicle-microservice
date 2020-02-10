package de.flick

import javax.annotation.security.PermitAll
import javax.enterprise.context.RequestScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@Path("/hello")
@RequestScoped()
class ExampleResource {
    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    private fun hello() = "hello"
}
