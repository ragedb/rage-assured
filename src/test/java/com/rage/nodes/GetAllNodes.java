/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rage.nodes;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;

public class GetAllNodes extends BaseTest {

    @Test
    public void GetAllNodesOnEmptyGraph() {
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
                post("/db/rage/schema/nodes/Dog").
                then().
                assertThat().
                statusCode(201);
        given().
                spec(requestSpec).
                when().
                post("/db/rage/schema/nodes/Dog/properties/name/string").
                then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
                when().
                post("/db/rage/schema/nodes/Dog/properties/age/integer").
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
                body("{ \"name\" : \"helene\", \"age\" : 41 }").with().contentType(ContentType.JSON).
                post("/db/rage/node/User/Helene").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("User")).
                body("key", is("Helene")).
                body("properties.age", is(41)).
                body("properties.name", is("helene"));

        given().
                spec(requestSpec).
                when().
                delete("/db/rage/node/User/Helene").
                then().
                assertThat().
                statusCode(204);

        given().
                spec(requestSpec).
                when().
                body("{ \"name\" : \"alex\", \"age\" : 41 }").with().contentType(ContentType.JSON).
                post("/db/rage/node/User/Alex").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("User")).
                body("key", is("Alex")).
                body("properties.age", is(41)).
                body("properties.name", is("alex"));

        given().
                spec(requestSpec).
                when().
                body("{ \"name\" : \"gabi\", \"age\" : 41 }").with().contentType(ContentType.JSON).
                post("/db/rage/node/User/Gabi").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("User")).
                body("key", is("Gabi")).
                body("properties.age", is(41)).
                body("properties.name", is("gabi"));

        given().
                spec(requestSpec).
                when().
                body("{ \"name\" : \"penny\", \"age\" : 2 }").with().contentType(ContentType.JSON).
                post("/db/rage/node/Dog/Penny").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("Dog")).
                body("key", is("Penny")).
                body("properties.age", is(2)).
                body("properties.name", is("penny"));

        given().
                spec(requestSpec).
                when().
                body("{ \"name\" : \"tyler\", \"age\" : 6 }").with().contentType(ContentType.JSON).
                post("/db/rage/node/Dog/Tyler").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("Dog")).
                body("key", is("Tyler")).
                body("properties.age", is(6)).
                body("properties.name", is("tyler"));

        given().
                spec(requestSpec).
                when().
                body("{ \"name\" : \"ronnie\", \"age\" : 6 }").with().contentType(ContentType.JSON).
                post("/db/rage/node/Dog/Ronnie").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("Dog")).
                body("key", is("Ronnie")).
                body("properties.age", is(6)).
                body("properties.name", is("ronnie"));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/nodes").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(6));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/nodes?skip=2").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(4));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/nodes?skip=3").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(3));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/nodes?skip=6").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(0));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/nodes?skip=99").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(0));
    }
}
