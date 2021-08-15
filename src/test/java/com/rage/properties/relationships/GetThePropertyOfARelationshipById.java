package com.rage.properties.relationships;

import com.rage.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GetThePropertyOfARelationshipById extends BaseTest {

    @Test
    public void GetThePropertyOfARelationshipByIdOnEmptyGraph() {
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
                get("/db/rage/relationship/1026/property/weight").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("weight", is(1.5f));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/relationship/2051/property/weight").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("weight", is(2.3f));
    }
}
