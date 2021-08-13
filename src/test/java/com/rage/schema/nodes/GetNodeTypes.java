package com.rage.schema.nodes;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetNodeTypes extends BaseTest {

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
                body("", empty()).
                body(equalTo("[]"));
    }

    @Test
    public void requestNodeTypesFromSingleNodeTypeGraph() {
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
                body("", hasItem("User"));
    }

    @Test
    public void requestNodeTypesFromMultipleNodeTypeGraph() {
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
                post("/db/rage/schema/nodes/Person").
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
                body("", hasItem("User")).
                body("", hasItem("Person"));
    }
}
