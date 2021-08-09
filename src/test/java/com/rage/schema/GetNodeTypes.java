package com.rage.schema;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GetNodeTypes {

    private static RequestSpecification requestSpec;

    @BeforeAll
    public static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(7243).
                build();
    }

    @BeforeEach
    public void clearGraph() {

        given().spec(requestSpec).delete("/db/rage/schema").then().statusCode(202);
    }

    @Test
    public void requestNodeTypesFromEmptyGraph() {

        given().
                spec(requestSpec).
        when().
                get("/db/rage/schema/nodes").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body(equalTo("[]"));
    }

    @Test
    public void requestNodeTypesFromGraph() {
        given().
                spec(requestSpec).
        when().
                post("/db/rage/schema/nodes/User").
        then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
        when().
                get("/db/rage/schema/nodes").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body(equalTo("[\"User\"]"));
    }
}
