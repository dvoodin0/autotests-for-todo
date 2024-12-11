package todo.apitest.tests.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import todo.apitest.dto.ToDoDto;

import java.lang.reflect.Field;
import java.util.HashMap;

import static todo.apitest.helper.data.generator.ToDoObjectGenerator.getFilledRequestBodyForCreateNewToDo;
import static todo.apitest.helper.Preconditions.getFilledRequestSpecificationWithoutAuth;
import static todo.apitest.steps.CommonToDoSteps.postToDoRequest;
import static todo.apitest.steps.CommonToDoSteps.validateRequestBodyEqualsItemFromList;
import static todo.apitest.steps.ValidationHttpStatuses.validate201ResponseStatus;
import static todo.apitest.steps.ValidationHttpStatuses.validate400ResponseStatus;


@DisplayName("Verify the functionality of the POST request for creating a new task")
@Epic("CRUD Operations for ToDo Management")
@Feature("Create a ToDo Item")
public class CreateNewTaskTest {

    @Test
    @Story("Create new Todo item")
    public void shouldReturnHttpStatusCreatedAndCreateNewToDoItem_whenAttemptToPostToDoWithAllFilledFields() {
        ToDoDto requestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>());

        Response response = postToDoRequest(requestSpecification);

        validate201ResponseStatus(response);
        validateRequestBodyEqualsItemFromList(requestBody.getId(), requestBody);

    }

    @Test
    @Story("Validation for creating a new ToDo with duplicate values")
    public void shouldReturnHttpStatusBadRequestAndGetError_whenAttemptToPostToDoWithDuplicateId() {
        ToDoDto firstRequestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification requestSpecificationForFirstRequest = getFilledRequestSpecificationWithoutAuth(firstRequestBody, new HashMap<>(), new HashMap<>());
        ToDoDto secondRequestBody = getFilledRequestBodyForCreateNewToDo(15);

        postToDoRequest(requestSpecificationForFirstRequest);
        secondRequestBody.setId(firstRequestBody.getId());
        RequestSpecification requestSpecificationForSecondRequest = getFilledRequestSpecificationWithoutAuth(secondRequestBody, new HashMap<>(), new HashMap<>());
        Response response = postToDoRequest(requestSpecificationForSecondRequest);

        validate400ResponseStatus(response);

    }

    @Test
    @Story("Validation for creating a new ToDo with duplicate values")
    public void shouldReturnHttpStatusCreatedAndCreateNewToDoItem_whenAttemptToPostToDoWithDuplicateTextValue() {
        ToDoDto firstRequestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification requestSpecificationForFirstRequest = getFilledRequestSpecificationWithoutAuth(firstRequestBody, new HashMap<>(), new HashMap<>());
        ToDoDto secondRequestBody = getFilledRequestBodyForCreateNewToDo(15);

        postToDoRequest(requestSpecificationForFirstRequest);
        secondRequestBody.setText(firstRequestBody.getText());
        RequestSpecification requestSpecificationForSecondRequest = getFilledRequestSpecificationWithoutAuth(secondRequestBody, new HashMap<>(), new HashMap<>());
        Response response = postToDoRequest(requestSpecificationForSecondRequest);

        validate201ResponseStatus(response);
        validateRequestBodyEqualsItemFromList(firstRequestBody.getId(), firstRequestBody);
        validateRequestBodyEqualsItemFromList(secondRequestBody.getId(), secondRequestBody);
    }

    @ParameterizedTest
    @ValueSource(strings = {"id", "text", "completed"})
    @Story("Field validation for required fields")
    public void shouldReturnHttpStatusBadRequestAndGetError_whenAttemptToPostToDoWithNullableRequiredField(String field) throws NoSuchFieldException, IllegalAccessException {
        ToDoDto requestBody = getFilledRequestBodyForCreateNewToDo(5);
        Field fieldToSet = ToDoDto.class.getDeclaredField(field);
        fieldToSet.setAccessible(true);
        fieldToSet.set(requestBody, null);
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>());

        Response response = postToDoRequest(requestSpecification);

        validate400ResponseStatus(response);

    }

}
