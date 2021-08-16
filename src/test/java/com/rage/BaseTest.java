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

package com.rage;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;

public class BaseTest {
    public static RequestSpecification requestSpec;

    @BeforeAll
    public static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(7243).
                build();
    }

    @BeforeEach
    public void clearGraph() {
        given().spec(requestSpec).delete("/db/rage/schema").then().statusCode(202);
    }

}
