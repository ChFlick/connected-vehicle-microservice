package de.flick.traffic

import de.flick.traffic.vehicles.VehicleRepository
import mu.KotlinLogging
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType.ARRAY
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import java.time.Instant
import javax.annotation.security.PermitAll
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON


@Path("/vehicles")
@RequestScoped
class TrafficController
@Inject
constructor(private val vehicleRepository: VehicleRepository) {
    private val logger = KotlinLogging.logger {}

    @GET
    @PermitAll
    @Produces(APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        content = [Content(
            mediaType = APPLICATION_JSON,
            schema = Schema(
                implementation = VehicleRepresentation::class,
                type = ARRAY)
        )]
    )
    fun getVehicles(): List<VehicleRepresentation> = vehicleRepository
        .findFromTillNow(Instant.now().minusMillis(1000))
        .map { it.toVehicleRepresentation() }
}
