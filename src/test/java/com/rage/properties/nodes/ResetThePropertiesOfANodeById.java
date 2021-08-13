package com.rage.properties.nodes;

import com.rage.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.nullValue;

public class ResetThePropertiesOfANodeById extends BaseTest {
    
    @Test
    public void ResetThePropertiesOfANodeByIdOnEmptyGraph() {
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
                post("/db/rage/schema/nodes/User/properties/awesome/boolean").
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
                post("/db/rage/schema/nodes/Person/properties/name/string").
                then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
                when().
                post("/db/rage/schema/nodes/Person/properties/age/integer").
                then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
                when().
                post("/db/rage/schema/nodes/Person/properties/awesome/boolean").
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
                body("id", is(1027)).
                body("type", is("User")).
                body("key", is("Max")).
                body("properties.age", is(42)).
                body("properties.name", is("max"));

        given().
                spec(requestSpec).
                when().
                body("{ \"name\" : \"helene\", \"age\" : 41 }").with().contentType(ContentType.JSON).
                post("/db/rage/node/Person/Helene").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("id", is(2050)).
                body("type", is("Person")).
                body("key", is("Helene")).
                body("properties.age", is(41)).
                body("properties.name", is("helene"));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/2050/properties").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("age", is(41)).
                body("name", is("helene"));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1027/properties").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("age", is(42)).
                body("name", is("max"));

        given().
                spec(requestSpec).
                when().
                body("{ \"awesome\" : true }").with().contentType(ContentType.JSON).
                post("/db/rage/node/1027/properties").
                then().
                assertThat().
                statusCode(204);

        given().
                spec(requestSpec).
                when().
                body("{ \"awesome\" : true }").with().contentType(ContentType.JSON).
                post("/db/rage/node/2050/properties").
                then().
                assertThat().
                statusCode(204);

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/2050/properties").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("awesome", is(true)).
                body("age", nullValue()).
                body("name", nullValue());

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1027/properties").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("awesome", is(true)).
                body("age", nullValue()).
                body("name", nullValue());
    }
}
