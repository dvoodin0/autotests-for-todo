package todo.apitest.helper.requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DeleteRequest {
    public static Response sendDeleteRequestWithReqSpec(RequestSpecification requestSpec) {
        Response resp = given()
                .spec(requestSpec)
                .delete();
        return resp;
    }
}
