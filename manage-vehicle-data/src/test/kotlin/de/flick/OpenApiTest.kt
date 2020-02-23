package de.flick

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test
import javax.ws.rs.core.HttpHeaders.ACCEPT
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response.Status.OK


@QuarkusTest
class OpenApiTest {

    @Test
    fun shouldPingOpenAPI() {
        given()
            .header(ACCEPT, APPLICATION_JSON)
            .`when`().get("/openapi")
            .then()
            .statusCode(OK.statusCode)
    }

    @Test
    fun shouldPingSwaggerUI() {
        given()
            .`when`().get("/swagger-ui")
            .then()
            .statusCode(OK.statusCode)
    }
}
