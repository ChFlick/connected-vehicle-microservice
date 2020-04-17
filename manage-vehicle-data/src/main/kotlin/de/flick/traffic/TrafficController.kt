package de.flick.traffic

import de.flick.traffic.vehicles.VehicleDTO
import de.flick.traffic.vehicles.VehicleRepository
import mu.KotlinLogging
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType.ARRAY
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import java.time.Instant
import java.time.ZonedDateTime
import javax.annotation.security.PermitAll
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType.APPLICATION_JSON


@Path("/vehicles")
@RequestScoped
class TrafficController
@Inject
constructor(private val vehicleRepository: VehicleRepository) {
    private val logger = KotlinLogging.logger {}

    @GET
    @Path("/carsSinceLastFiveSeconds")
    @PermitAll
    @Produces(APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        content = [Content(
            mediaType = APPLICATION_JSON,
            schema = Schema(
                implementation = VehicleDTO::class,
                type = ARRAY)
        )]
    )
    fun getVehiclesLastFiveSeconds(): List<VehicleDTO> = vehicleRepository
        .findFromTillNow(Instant.now().minusMillis(5000))

    @GET
    @Path("/busesBetween")
    @PermitAll
    @Produces(APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        content = [Content(
            mediaType = APPLICATION_JSON,
            schema = Schema(
                implementation = VehicleDTO::class,
                type = ARRAY)
        )]
    )
    fun getBusesBetween(
        @Parameter(
            required = true,
            example = "2020-03-15T18:12:25.026Z",
            schema = Schema(implementation = ZonedDateTime::class)
        )
        @QueryParam("start") start: String,
        @Parameter(
            required = true,
            example = "2020-03-15T18:12:25.026Z",
            schema = Schema(implementation = ZonedDateTime::class)
        )
        @QueryParam("end") end: String
    ): List<VehicleDTO> = vehicleRepository
        .findBusesBetween(ZonedDateTime.parse(start), ZonedDateTime.parse(end))
}

