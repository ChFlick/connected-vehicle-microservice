package de.flick.traffic

import de.flick.connectors.InfluxDBResource
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import javax.ws.rs.core.HttpHeaders.ACCEPT
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response.Status.OK

@QuarkusTest
@QuarkusTestResource(InfluxDBResource::class)
class TrafficControllerTest {

    @Test
    fun carsSinceLastFiveSeconds_IsAvailable() {
        // @formatter:off
        given()
            .header(ACCEPT, APPLICATION_JSON)
        .`when`()
            .get("/vehicles/carsSinceLastFiveSeconds")
        .then()
            .statusCode(OK.statusCode)
            .body(notNullValue())
        // @formatter:on
    }

    @Test
    fun busesBetween_IsAvailable() {
        // @formatter:off
        given()
            .header(ACCEPT, APPLICATION_JSON)
            .params(mapOf(
                "start" to ZonedDateTime.now().minusDays(1),
                "end" to ZonedDateTime.now()
            ))
        .`when`()
            .get("/vehicles/busesBetween")
        .then()
            .statusCode(OK.statusCode)
            .body(notNullValue())
        // @formatter:on
    }
}
