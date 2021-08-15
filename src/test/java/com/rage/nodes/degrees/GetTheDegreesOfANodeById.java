package com.rage.nodes.degrees;

import com.rage.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GetTheDegreesOfANodeById extends BaseTest {

    @Test
    public void GetTheDegreesOfANodeByIdOnEmptyGraph() {
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
                post("/db/rage/schema/relationships/KNOWS").
                then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
                when().
                post("/db/rage/schema/relationships/KNOWS/properties/weight/double").
                then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
                when().
                post("/db/rage/schema/relationships/KNOWS/properties/valid/boolean").
                then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
                when().
                post("/db/rage/node/User/Max").
                then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
                when().
                post("/db/rage/node/User/Helene").
                then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
                when().
                body("{ \"weight\" : 1.5, \"valid\" : true }").with().contentType(ContentType.JSON).
                post("/db/rage/node/User/Helene/relationship/User/Max/LOVES").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("LOVES")).
                body("id", is(1026)).
                body("properties.weight", is(1.5f)).
                body("properties.valid", is(true));

        given().
                spec(requestSpec).
                when().
                body("{ \"weight\" : 2.3, \"valid\" : true }").with().contentType(ContentType.JSON).
                post("/db/rage/node/User/Max/relationship/User/Helene/KNOWS").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("KNOWS")).
                body("id", is(2051)).
                body("properties.weight", is(2.3f)).
                body("properties.valid", is(true));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/degree").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("degree", equalTo(2));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/degree/all").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("degree", equalTo(2));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/degree/out").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("degree", equalTo(1));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/degree/in").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("degree", equalTo(1));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/degree/out/LOVES").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("degree", equalTo(1));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/degree/in/KNOWS").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("degree", equalTo(1));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/degree/all/KNOWS&LOVES").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("degree", equalTo(2));
    }
}