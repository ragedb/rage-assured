package com.rage.schema;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;

public class GetNodeType {

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
    public void requestNodeTypeFromEmptyGraph() {

        given().
                spec(requestSpec).
        when().
                get("/db/rage/schema/nodes/User").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("$", not(hasItem("name")));
    }

    @Test
    public void requestEmptyNodeTypesFromSingleNodeTypeGraph() {
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
                get("/db/rage/schema/nodes/User").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("", not(hasItem("name")));
    }

    @Test
    public void requestNonEmptyNodeTypeFromSingleNodeTypeGraph() {
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
                post("/db/rage/schema/nodes/User/properties/name/string").
        then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
        when().
                post("/db/rage/schema/nodes/User/properties/age/integer").
        then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
        when().
                get("/db/rage/schema/nodes/User").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("age", is("integer")).
                body("name", is("string"));
    }
}
