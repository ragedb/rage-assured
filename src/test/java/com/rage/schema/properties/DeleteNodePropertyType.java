package com.rage.schema.properties;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;

public class DeleteNodePropertyType extends BaseTest {

    @Test
    public void DeleteNodePropertyTypeOnEmptyGraph() {
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
                get("/db/rage/schema/nodes/User/properties/name").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("name", is("string"));

        given().
                spec(requestSpec).
                when().
                delete("/db/rage/schema/nodes/User/properties/name").
                then().
                assertThat().
                statusCode(204);

        given().
                spec(requestSpec).
                when().
                get("/db/rage/schema/nodes/User/properties/name").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("", not(hasItem("name")));
    }
}
