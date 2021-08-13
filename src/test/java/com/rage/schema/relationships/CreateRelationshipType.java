package com.rage.schema.relationships;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class CreateRelationshipType extends BaseTest {

    @Test
    public void CreateRelationshipTypeOnEmptyGraph() {
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
}
