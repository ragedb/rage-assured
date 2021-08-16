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

package com.rage.schema.properties;

import com.rage.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteRelationshipPropertyType extends BaseTest {

    @Test
    public void DeleteRelationshipPropertyTypeOnEmptyGraph() {
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
                get("/db/rage/schema/relationships/LOVES/properties/weight").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("weight", is("double"));

        given().
                spec(requestSpec).
                when().
                delete("/db/rage/schema/relationships/LOVES/properties/weight").
                then().
                assertThat().
                statusCode(204);

        given().
                spec(requestSpec).
                when().
                get("/db/rage/schema/relationships/LOVES/properties/weight").
                then().
                assertThat().
                statusCode(200).
                contentType(equalTo("application/json")).
                body("", not(hasItem("weight")));
    }
}
