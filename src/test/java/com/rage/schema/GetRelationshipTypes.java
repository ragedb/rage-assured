package com.rage.schema;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetRelationshipTypes extends BaseTest {

    @Test
    public void requestRelationshipTypesFromEmptyGraph() {

        given().
                spec(requestSpec).
        when().
                get("/db/rage/schema/relationships").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("", empty()).
                body(equalTo("[]"));
    }

    @Test
    public void requestRelationshipTypesFromSingleRelationshipTypeGraph() {
        given().
                spec(requestSpec).
        when().
                post("/db/rage/schema/relationships/LOVES").
        then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
        when().
                get("/db/rage/schema/relationships").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("", hasItem("LOVES"));
    }

    @Test
    public void requestRelationshipTypesFromMultipleRelationshipTypeGraph() {
        given().
                spec(requestSpec).
                when().
                post("/db/rage/schema/relationships/LOVES").
                then().
                assertThat().
                statusCode(201);
        given().
                spec(requestSpec).
                when().
                post("/db/rage/schema/relationships/LIKES").
                then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
        when().
                get("/db/rage/schema/relationships").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("", hasItem("LOVES")).
                body("", hasItem("LIKES"));
    }
}
