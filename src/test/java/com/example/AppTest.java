package com.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
public class AppTest {

    @Container
    public GenericContainer<?> mockServer = new GenericContainer<>("mockserver/mockserver:latest")
            .withExposedPorts(1080);

    @Test
    public void testAPI() {
        String baseUri = "http://" + mockServer.getHost() + ":" + mockServer.getMappedPort(1080);

        RestAssured.baseURI = baseUri;

        Response response = given()
                .when()
                .get("/some-endpoint")
                .then()
                .statusCode(200)
                .body("key", equalTo("value"))
                .extract().response();

        System.out.println(response.asString());
    }
}
