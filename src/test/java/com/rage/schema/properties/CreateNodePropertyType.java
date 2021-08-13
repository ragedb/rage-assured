package com.rage.schema.properties;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateNodePropertyType extends BaseTest {

    @Test
    public void CreateNodePropertyTypeOnEmptyGraph() {
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
