package com.rage.relationships;

import com.rage.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GetTheRelationshipsOfANodeById extends BaseTest {

    @Test
    public void GetTheRelationshipsOfANodeByIdOnEmptyGraph() {
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
                get("/db/rage/node/1026/relationships").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(2)).
                body("[0].type", is("LOVES")).
                body("[1].type", is("KNOWS")).
                body("[0].id", is(1026)).
                body("[1].id", is(2051)).
                body("[0].properties.weight", is(1.5f)).
                body("[0].properties.valid", is(true)).
                body("[1].properties.weight", is(2.3f)).
                body("[1].properties.valid", is(true));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/relationships/all").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(2)).
                body("[0].type", is("LOVES")).
                body("[1].type", is("KNOWS")).
                body("[0].id", is(1026)).
                body("[1].id", is(2051)).
                body("[0].properties.weight", is(1.5f)).
                body("[0].properties.valid", is(true)).
                body("[1].properties.weight", is(2.3f)).
                body("[1].properties.valid", is(true));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/relationships/out").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1)).
                body("[0].type", is("LOVES")).
                body("[0].id", is(1026)).
                body("[0].properties.weight", is(1.5f)).
                body("[0].properties.valid", is(true));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/relationships/in").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1)).
                body("[0].type", is("KNOWS")).
                body("[0].id", is(2051)).
                body("[0].properties.weight", is(2.3f)).
                body("[0].properties.valid", is(true));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/relationships/out/LOVES").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1)).
                body("[0].type", is("LOVES")).
                body("[0].id", is(1026)).
                body("[0].properties.weight", is(1.5f)).
                body("[0].properties.valid", is(true));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/relationships/in/KNOWS").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1)).
                body("[0].type", is("KNOWS")).
                body("[0].id", is(2051)).
                body("[0].properties.weight", is(2.3f)).
                body("[0].properties.valid", is(true));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1026/relationships/all/KNOWS&LOVES").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(2)).
                body("[0].type", is("LOVES")).
                body("[1].type", is("KNOWS")).
                body("[0].id", is(1026)).
                body("[1].id", is(2051)).
                body("[0].properties.weight", is(1.5f)).
                body("[0].properties.valid", is(true)).
                body("[1].properties.weight", is(2.3f)).
                body("[1].properties.valid", is(true));
    }
}
