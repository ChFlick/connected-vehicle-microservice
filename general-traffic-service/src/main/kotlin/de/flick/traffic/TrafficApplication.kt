package de.flick.traffic

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Contact
import org.eclipse.microprofile.openapi.annotations.info.Info
import org.eclipse.microprofile.openapi.annotations.servers.Server
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/traffic")
@OpenAPIDefinition(
    info = Info(title = "Traffic API",
        description = "This API allows CRUD operations on SUMO Traffic data",
        version = "1.0",
        contact = Contact(name = "Christoph Flick")),
    servers = [
        Server(url = "http://localhost:8084")
    ]
)
class TrafficApplication : Application()
