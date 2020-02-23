package de.flick.traffic

import de.flick.traffic.vehicles.VehicleDTO
import de.flick.traffic.vehicles.VehicleRepository
import mu.KotlinLogging
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType.ARRAY
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import javax.annotation.security.PermitAll
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON


private val logger = KotlinLogging.logger {}

@Path("/vehicles")
@RequestScoped()
class ExampleResource
@Inject
constructor(private val vehicleRepository: VehicleRepository) {

    @GET()
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
    fun getVehicles(): List<VehicleDTO> = vehicleRepository.findByMinutesFromNow(5)
}
