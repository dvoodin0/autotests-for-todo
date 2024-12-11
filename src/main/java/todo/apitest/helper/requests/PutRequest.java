package todo.apitest.helper.requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PutRequest {
    public static Response sendPutRequestWithReqSpec(RequestSpecification requestSpec) {
        Response resp = given()
                .spec(requestSpec)
                .put();
        return resp;
    }
}
