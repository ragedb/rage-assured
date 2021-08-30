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

package com.rage.relationships;

import com.rage.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class FindRelationshipsOfAType extends BaseTest {

    @Test
    public void FindRelationshipsOfATypeOnEmptyGraph() {
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
                post("/db/rage/node/User/Penny").
                then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
                when().
                post("/db/rage/node/User/Tyler").
                then().
                assertThat().
                statusCode(201);

        given().
                spec(requestSpec).
                when().
                post("/db/rage/node/User/Ronnie").
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
                body("properties.weight", is(2.3f)).
                body("properties.valid", is(true));

        given().
                spec(requestSpec).
                when().
                body("{ \"weight\" : 3.1, \"valid\" : true }").with().contentType(ContentType.JSON).
                post("/db/rage/node/User/Max/relationship/User/Penny/KNOWS").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("KNOWS")).
                body("properties.weight", is(3.1f)).
                body("properties.valid", is(true));

        given().
                spec(requestSpec).
                when().
                body("{ \"weight\" : 2.3, \"valid\" : true }").with().contentType(ContentType.JSON).
                post("/db/rage/node/User/Max/relationship/User/Ronnie/KNOWS").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("KNOWS")).
                body("properties.weight", is(2.3f)).
                body("properties.valid", is(true));

        given().
                spec(requestSpec).
                when().
                body("{ \"weight\" : 2.3, \"valid\" : false }").with().contentType(ContentType.JSON).
                post("/db/rage/node/User/Max/relationship/User/Tyler/KNOWS").
                then().
                assertThat().
                statusCode(201).
                contentType(equalTo("application/json")).
                body("type", is("KNOWS")).
                body("id", is(201328643)).
                body("properties.weight", is(2.3f)).
                body("properties.valid", is(false));

        given().
                spec(requestSpec).
                when().
                delete("/db/rage/relationship/201328643/property/weight").
                then().
                assertThat().
                statusCode(204);

        given().
                spec(requestSpec).
                when().
                get("/db/rage/relationships/LOVES").
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
                post("/db/rage/relationships/KNOWS/weight/IS_NULL").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(1));

        given().
                spec(requestSpec).
                when().
                post("/db/rage/relationships/KNOWS/weight/NOT_IS_NULL").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("size()", equalTo(3));
    }
}
