package todo.apitest.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static todo.apitest.asserts.AssertsForHttpClientParametersAndStatusCode.assertHttpClientParametersStatusCode200;
import static todo.apitest.asserts.AssertsForHttpClientParametersAndStatusCode.assertHttpClientParametersStatusCode201;
import static todo.apitest.asserts.AssertsForHttpClientParametersAndStatusCode.assertHttpClientParametersStatusCode204;
import static todo.apitest.asserts.AssertsForHttpClientParametersAndStatusCode.assertHttpClientParametersStatusCode400;
import static todo.apitest.asserts.AssertsForHttpClientParametersAndStatusCode.assertHttpClientParametersStatusCode401;
import static todo.apitest.asserts.AssertsForHttpClientParametersAndStatusCode.assertHttpClientParametersStatusCode404;

public class ValidationHttpStatuses {

    @Step("Validate 201 response status")
    public static void validate201ResponseStatus(Response response) {
        assertHttpClientParametersStatusCode201(response);
    }

    @Step("Validate 400 response status")
    public static void validate400ResponseStatus(Response response) {
        assertHttpClientParametersStatusCode400(response);
    }

    @Step("Validate 200 response status")
    public static void validate200ResponseStatus(Response response) {
        assertHttpClientParametersStatusCode200(response);
    }

    @Step("Validate 404 response status")
    public static void validate404ResponseStatus(Response response) {
        assertHttpClientParametersStatusCode404(response);
    }

    @Step("Validate 204 response status")
    public static void validate204ResponseStatus(Response response) {
        assertHttpClientParametersStatusCode204(response);
    }

    @Step("Validate 401 response status")
    public static void validate401ResponseStatus(Response response) {
        assertHttpClientParametersStatusCode401(response);
    }
}
