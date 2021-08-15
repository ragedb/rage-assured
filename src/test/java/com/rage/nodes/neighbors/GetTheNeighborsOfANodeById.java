package com.rage.nodes.neighbors;

import com.rage.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GetTheNeighborsOfANodeById extends BaseTest {

    @Test
    public void GetTheNeighborsOfANodeByIdOnEmptyGraph() {
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
                body("{ \"name\" : \"max\", \"age\" : 42 }").with().contentType(ContentType.JSON).
                post("/db/rage/node/User/Max").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("User")).
                body("key", is("Max")).
                body("properties.age", is(42)).
                body("properties.name", is("max"));

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
                get("/db/rage/node/1026/neighbors").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(2)).
                body("[0].type", is("User")).
                body("[0].key", is("Max")).
                body("[0].properties.age", is(42)).
                body("[0].properties.name", is("max"));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/neighbors/all").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(2)).
                body("[0].type", is("User")).
                body("[0].key", is("Max")).
                body("[0].properties.age", is(42)).
                body("[0].properties.name", is("max"));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/neighbors/out").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1)).
                body("[0].type", is("User")).
                body("[0].key", is("Max")).
                body("[0].properties.age", is(42)).
                body("[0].properties.name", is("max"));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/neighbors/in").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1)).
                body("[0].type", is("User")).
                body("[0].key", is("Max")).
                body("[0].properties.age", is(42)).
                body("[0].properties.name", is("max"));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/neighbors/out/LOVES").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1)).
                body("[0].type", is("User")).
                body("[0].key", is("Max")).
                body("[0].properties.age", is(42)).
                body("[0].properties.name", is("max"));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/neighbors/in/KNOWS").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1)).
                body("[0].type", is("User")).
                body("[0].key", is("Max")).
                body("[0].properties.age", is(42)).
                body("[0].properties.name", is("max"));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/neighbors/all/KNOWS&LOVES").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(2)).
                body("[0].type", is("User")).
                body("[0].key", is("Max")).
                body("[0].properties.age", is(42)).
                body("[0].properties.name", is("max"));
    }
}
