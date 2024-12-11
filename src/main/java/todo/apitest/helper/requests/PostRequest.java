package todo.apitest.helper.requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class PostRequest {

    public static Response sendPostRequestWithReqSpec(RequestSpecification requestSpec) {
        Response resp = given()
                .spec(requestSpec)
                .post();
        return resp;
    }
}
