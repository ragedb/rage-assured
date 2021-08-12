package com.rage.schema;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetRelationshipType extends BaseTest {

    @Test
    public void requestRelationshipTypeFromEmptyGraph() {

        given().
                spec(requestSpec).
        when().
                get("/db/rage/schema/relationships/LOVES").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("$", not(hasItem("weight")));
    }

    @Test
    public void requestEmptyRelationshipTypesFromSingleRelationshipTypeGraph() {
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
                get("/db/rage/schema/relationships/LOVES").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("", not(hasItem("weight")));
    }

    @Test
    public void requestNonEmptyRelationshipTypeFromSingleRelationshipTypeGraph() {
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
                post("/db/rage/schema/relationships/LOVES/properties/weight/double").
        then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
        when().
                post("/db/rage/schema/relationships/LOVES/properties/valid/boolean").
        then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
        when().
                get("/db/rage/schema/relationships/LOVES").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("valid", is("boolean")).
                body("weight", is("double"));
    }
}
