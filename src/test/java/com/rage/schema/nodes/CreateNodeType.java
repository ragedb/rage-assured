package com.rage.schema.nodes;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class CreateNodeType extends BaseTest {

    @Test
    public void CreateNodeTypeOnEmptyGraph() {
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
}
