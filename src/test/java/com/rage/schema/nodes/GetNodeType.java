package com.rage.schema.nodes;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetNodeType extends BaseTest {

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
