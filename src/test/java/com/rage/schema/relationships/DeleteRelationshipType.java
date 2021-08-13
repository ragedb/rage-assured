package com.rage.schema.relationships;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteRelationshipType extends BaseTest {

    @Test
    public void DeleteRelationshipTypeOnEmptyGraph() {
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

        given().
                spec(requestSpec).
        when().
                delete("/db/rage/schema/relationships/LOVES").
        then().
                assertThat().
                statusCode(204);

        given().
                spec(requestSpec).
        when().
                get("/db/rage/schema/relationships").
        then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("", not(hasItem("LOVES")));
    }
}
