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
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class FindNodesOfAType extends BaseTest {
    
    @Test
    public void FindNodesOfATypeOnEmptyGraph() {
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
                post("/db/rage/node/User/Penny").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("User")).
                body("key", is("Penny")).
                body("properties.age", is(2)).
                body("properties.name", is("penny"));

        given().
                spec(requestSpec).
                when().
                delete("/db/rage/node/User/Penny/property/age").
                then().
                assertThat().
                statusCode(204);

        given().
                spec(requestSpec).
                when().
                post("/db/rage/nodes/User/age/IS_NULL").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1));

        given().
                spec(requestSpec).
                when().
                post("/db/rage/nodes/User/age/NOT_IS_NULL").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(3));

        given().
                spec(requestSpec).
                when().
                body("41").with().contentType(ContentType.JSON).
                post("/db/rage/nodes/User/age/EQ").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(2)).
                body("[0].type", is("User")).
                body("[0].key", is("Gabi")).
                body("[0].properties.age", is(41)).
                body("[0].properties.name", is("gabi")).
                body("[1].type", is("User")).
                body("[1].key", is("Alex")).
                body("[1].properties.age", is(41)).
                body("[1].properties.name", is("alex"));

        given().
                spec(requestSpec).
                when().
                body("42").with().contentType(ContentType.JSON).
                post("/db/rage/nodes/User/age/EQ").
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
                body("41").with().contentType(ContentType.JSON).
                post("/db/rage/nodes/User/age/GT").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1));

        given().
                spec(requestSpec).
                when().
                body("41").with().contentType(ContentType.JSON).
                post("/db/rage/nodes/User/age/GTE").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(3));

        given().
                spec(requestSpec).
                when().
                body("42").with().contentType(ContentType.JSON).
                post("/db/rage/nodes/User/age/LT").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(2));

        given().
                spec(requestSpec).
                when().
                body("42").with().contentType(ContentType.JSON).
                post("/db/rage/nodes/User/age/LTE").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(3));

        given().
                spec(requestSpec).
                when().
                body("42").with().contentType(ContentType.JSON).
                post("/db/rage/nodes/User/age/NEQ").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(2));

        given().
                spec(requestSpec).
                when().
                body("1").with().contentType(ContentType.JSON).
                post("/db/rage/nodes/User/age/GTE?limit=1&skip=2").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1));
    }
}
