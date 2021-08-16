# rage-assured
HTTP API Test Suite

We are using [rest-assured](https://rest-assured.io/) to test RageDB.

Currently, very few tests exist. I could really use some help writing more of these and looking for edge-cases we may not handle correctly.

This test suite will eventually run during the RageDB github actions to verify we didn't break anything.

## Running tests

Make sure an instance of RageDB is running on localhost and then:

        mvn test

Or bring up the project in IntelliJ or your favorite IDE and run it from there.