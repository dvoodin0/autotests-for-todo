package todo.apitest.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import todo.apitest.config.ApiConfig;
import todo.apitest.dto.ToDoDto;

import java.math.BigInteger;
import java.util.List;


import static todo.apitest.asserts.AssertsForToDoItems.assertExpectedFieldValueEqualsActualFieldValue;
import static todo.apitest.asserts.AssertsForToDoItems.assertExpectedObjectEqualsActualObjectWithoutIgnoringFields;
import static todo.apitest.asserts.AssertsForToDoItems.assertItemDoesNotExistAfterDelete;
import static todo.apitest.asserts.AssertsForToDoItems.assertItemExist;
import static todo.apitest.asserts.AssertsForToDoItems.assertItemListEqualsGotList;
import static todo.apitest.asserts.AssertsForToDoItems.assertObjectIsEmpty;
import static todo.apitest.asserts.AssertsForToDoItems.assertRequestBodyEqualsItemFromList;
import static todo.apitest.helper.requests.DeleteRequest.sendDeleteRequestWithReqSpec;
import static todo.apitest.helper.requests.GetRequest.sendGetRequestWithReqSpec;
import static todo.apitest.helper.requests.PostRequest.sendPostRequestWithReqSpec;
import static todo.apitest.helper.requests.PutRequest.sendPutRequestWithReqSpec;

public class CommonToDoSteps {

    public static String HOST = ConfigFactory.create(ApiConfig.class).getUrl();
    public static String TODO_PATH = "/todos";
    private static final String PREFIX = "/";


    @Step("Send a POST request to create a new TODO with request body")
    public static Response postToDoRequest(RequestSpecification requestSpecification) {
        String url = HOST + TODO_PATH;
        Response response = sendPostRequestWithReqSpec(requestSpecification.baseUri(url));
        return response;
    }

    @Step("Send a PUT request to update an existing TODO with id = {taskId}")
    public static Response putToDoRequest(RequestSpecification requestSpecification, BigInteger taskId) {
        String url = HOST + TODO_PATH + PREFIX + taskId;
        Response response = sendPutRequestWithReqSpec(requestSpecification.baseUri(url));
        return response;
    }

    @Step("Send a GET request to get list of TODOs")
    public static Response getListOfToDoRequest(RequestSpecification requestSpecification) {
        String url = HOST + TODO_PATH;
        Response response = sendGetRequestWithReqSpec(requestSpecification.baseUri(url));
        return response;
    }

    @Step("Send a DELETE request to delete an existing TODO with id = {taskId}")
    public static Response deleteToDoRequest(RequestSpecification requestSpecification, BigInteger taskId) {
        String url = HOST + TODO_PATH + PREFIX + taskId;
        Response response = sendDeleteRequestWithReqSpec(requestSpecification.baseUri(url));
        return response;
    }

    @Step("Validate successful deletion of item")
    public static void validateDoesNotItemExists(BigInteger toDoId) {
        assertItemDoesNotExistAfterDelete(toDoId);
    }

    @Step("Validate item existence")
    public static void validateItemExists(BigInteger toDoId) {
        assertItemExist(toDoId);
    }

    @Step("Verification that the obtained task list matches the expected task list")
    public static void validateGotItemListEqualsExpectedItemList(List<ToDoDto> expectedList, List<ToDoDto> actualList) {
        assertItemListEqualsGotList(expectedList, actualList);
    }

    @Step("Validation that the task body matches the task body from the request")
    public static void validateRequestBodyEqualsItemFromList(BigInteger toDoId, ToDoDto requestBody) {
        assertRequestBodyEqualsItemFromList(toDoId, requestBody);
    }

    @Step("Validate that both objects are equal in all fields")
    public static <T> void validateExpectedObjectEqualsActualObjectWithoutIgnoringFields(T expected, T actual) {
        assertExpectedObjectEqualsActualObjectWithoutIgnoringFields(expected, actual);
    }

    @Step("Validate that both objects are equal in all fields")
    public static <T> void validateExpectedFieldValueEqualsActualFieldValue(T expected, T actual) {
        assertExpectedFieldValueEqualsActualFieldValue(expected, actual);
    }

    @Step("Validate that the object is null")
    public static <T> void validateObjectIsNull(T object) {
        assertObjectIsEmpty(object);
    }

}
