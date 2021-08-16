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

package com.rage.properties.nodes;

import com.rage.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SetThePropertyOfANodeById extends BaseTest {
    
    @Test
    public void SetThePropertyOfANodeByIdOnEmptyGraph() {
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
                post("/db/rage/node/Person/Helene").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("Person")).
                body("key", is("Helene")).
                body("properties.age", is(41)).
                body("properties.name", is("helene"));

        given().
                spec(requestSpec).
                when().
                body("\"roger\"").with().contentType(ContentType.JSON).
                put("/db/rage/node/1027/property/name").
                then().
                assertThat().
                statusCode(204);

        given().
                spec(requestSpec).
                when().
                body("\"laura\"").with().contentType(ContentType.JSON).
                put("/db/rage/node/2050/property/name").
                then().
                assertThat().
                statusCode(204);

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/2050").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("type", is("Person")).
                body("key", is("Helene")).
                body("properties.age", is(41)).
                body("properties.name", is("laura"));

        given().
                spec(requestSpec).
                when().
                get("/db/rage/node/1027").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("type", is("User")).
                body("key", is("Max")).
                body("properties.age", is(42)).
                body("properties.name", is("roger"));
    }
}
