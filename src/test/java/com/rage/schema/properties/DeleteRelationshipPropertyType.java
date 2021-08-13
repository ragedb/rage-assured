package com.rage.schema.properties;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteRelationshipPropertyType extends BaseTest {

    @Test
    public void DeleteRelationshipPropertyTypeOnEmptyGraph() {
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
                get("/db/rage/schema/relationships/LOVES/properties/weight").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("weight", is("double"));

        given().
                spec(requestSpec).
                when().
                delete("/db/rage/schema/relationships/LOVES/properties/weight").
                then().
                assertThat().
                statusCode(204);

        given().
                spec(requestSpec).
                when().
                get("/db/rage/schema/relationships/LOVES/properties/weight").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("", not(hasItem("weight")));
    }
}
