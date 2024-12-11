package todo.apitest.helper.requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static todo.apitest.constants.Properties.ACCEPT_JSON_TYPE;

public class GetRequest {
    public static Response sendGetRequestWithReqSpec(RequestSpecification requestSpec) {
        Response resp = given()
                .spec(requestSpec)
                .accept(ACCEPT_JSON_TYPE)
                .get();
        return resp;
    }
}
