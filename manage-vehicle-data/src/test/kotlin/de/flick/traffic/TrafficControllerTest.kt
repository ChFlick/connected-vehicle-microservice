package de.flick.traffic

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.Test

@QuarkusTest
class TrafficControllerTest {

    @Test
    fun getVehicles() {
        given()
            .`when`()
                .get("/traffic/vehicles")
            .then()
                .statusCode(200)
                .body(notNullValue())
    }
}
