package de.flick.traffic

import de.flick.traffic.vehicles.VehicleDTO
import de.flick.traffic.vehicles.VehicleRepository
import mu.KotlinLogging
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType.ARRAY
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import java.time.ZonedDateTime
import javax.annotation.security.PermitAll
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON


@Path("/vehicles")
@RequestScoped
class TrafficController
@Inject
constructor(private val vehicleRepository: VehicleRepository) {
    private val logger = KotlinLogging.logger {}

    @GET
    @Path("/vehiclesBetween")
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
    fun getVehiclesBetween(
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
        .findVehiclesBetween(ZonedDateTime.parse(start), ZonedDateTime.parse(end))

    @GET
    @Path("/vehiclesMeanDataBetween")
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
    fun getVehiclesMeanDataBetween(
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
        @QueryParam("end") end: String,
        @Parameter(
            required = false,
            example = "5m"
        )
        @DefaultValue("5m")
        @QueryParam("meanBy") meanBy: String
    ): List<VehicleDTO> = vehicleRepository
        .meanVehicleDataBetween(ZonedDateTime.parse(start), ZonedDateTime.parse(end), meanBy)
}

