package com.rage.lua;

import com.rage.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ExecuteLuaScript extends BaseTest {

    @Test
    public void ExecuteLuaScriptOnEmptyGraph() {
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
                post("/db/rage/schema/nodes/Person").
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
                post("/db/rage/node/Person/Helene").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("Person")).
                body("key", is("Helene"));

        given().
                spec(requestSpec).
        when().
                body("NodeGet(\"User\",\"Max\")").
                post("/db/rage/lua").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("[0].type", is("User")).
                body("[0].key", is("Max")).
                body("[0].properties.age", is(42)).
                body("[0].properties.name", is("max"));

        given().
                spec(requestSpec).
        when().
                body("NodeGet(\"User\",\"Max\"), NodeGet(\"Person\",\"Helene\")").
                post("/db/rage/lua").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("[0].type", is("User")).
                body("[0].key", is("Max")).
                body("[0].properties.age", is(42)).
                body("[0].properties.name", is("max")).
                body("[1].type", is("Person")).
                body("[1].key", is("Helene"));

    }

}
