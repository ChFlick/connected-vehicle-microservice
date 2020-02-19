package de.flick.traffic

import de.flick.traffic.vehicles.VehicleDTO
import de.flick.traffic.vehicles.VehicleRepository
import mu.KotlinLogging
import javax.annotation.security.PermitAll
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


private val logger = KotlinLogging.logger {}

@Path("/traffic")
@RequestScoped()
class ExampleResource
@Inject
constructor(private val vehicleRepository: VehicleRepository) {

    @Path("/vehicles")
    @GET()
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    fun getVehicles(): List<VehicleDTO> = vehicleRepository.findByMinutesFromNow(5)
}
