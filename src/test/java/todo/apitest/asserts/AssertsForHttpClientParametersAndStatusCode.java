package todo.apitest.asserts;

import io.restassured.response.Response;


import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static todo.apitest.constants.ResponseValidationParameters.HTTP_VERSION;
import static todo.apitest.constants.ResponseValidationParameters.RF_BAD_REQUEST;
import static todo.apitest.constants.ResponseValidationParameters.RF_CREATED;
import static todo.apitest.constants.ResponseValidationParameters.RF_NOT_FOUND;
import static todo.apitest.constants.ResponseValidationParameters.RF_NO_CONTENT;
import static todo.apitest.constants.ResponseValidationParameters.RF_OK;
import static todo.apitest.constants.ResponseValidationParameters.RF_UNAUTHORIZED;


public class AssertsForHttpClientParametersAndStatusCode {

    public static void assertHttpClientParametersStatusCode201(Response response) {
        assertSoftly(softly -> {
            softly.assertThat(response.statusLine().substring(0, 8)).isEqualTo(HTTP_VERSION);
            softly.assertThat(response.statusCode()).isEqualTo(SC_CREATED);
            softly.assertThat(response.statusLine().substring(13)).isEqualTo(RF_CREATED);
        });
    }

    public static void assertHttpClientParametersStatusCode400(Response response) {
        assertSoftly(softly -> {
            softly.assertThat(response.statusLine().substring(0, 8)).isEqualTo(HTTP_VERSION);
            softly.assertThat(response.statusCode()).isEqualTo(SC_BAD_REQUEST);
            softly.assertThat(response.statusLine().substring(13)).isEqualTo(RF_BAD_REQUEST);
        });
    }

    public static void assertHttpClientParametersStatusCode200(Response response) {
        assertSoftly(softly -> {
            softly.assertThat(response.statusLine().substring(0, 8)).isEqualTo(HTTP_VERSION);
            softly.assertThat(response.statusCode()).isEqualTo(SC_OK);
            softly.assertThat(response.statusLine().substring(13)).isEqualTo(RF_OK);
        });
    }

    public static void assertHttpClientParametersStatusCode404(Response response) {
        assertSoftly(softly -> {
            softly.assertThat(response.statusLine().substring(0, 8)).isEqualTo(HTTP_VERSION);
            softly.assertThat(response.statusCode()).isEqualTo(SC_NOT_FOUND);
            softly.assertThat(response.statusLine().substring(13)).isEqualTo(RF_NOT_FOUND);
        });
    }

    public static void assertHttpClientParametersStatusCode204(Response response) {
        assertSoftly(softly -> {
            softly.assertThat(response.statusLine().substring(0, 8)).isEqualTo(HTTP_VERSION);
            softly.assertThat(response.statusCode()).isEqualTo(SC_NO_CONTENT);
            softly.assertThat(response.statusLine().substring(13)).isEqualTo(RF_NO_CONTENT);
        });
    }

    public static void assertHttpClientParametersStatusCode401(Response response) {
        assertSoftly(softly -> {
            softly.assertThat(response.statusLine().substring(0, 8)).isEqualTo(HTTP_VERSION);
            softly.assertThat(response.statusCode()).isEqualTo(SC_UNAUTHORIZED);
            softly.assertThat(response.statusLine().substring(13)).isEqualTo(RF_UNAUTHORIZED);
        });
    }

}
