package todo.apitest.tests.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import todo.apitest.dto.ToDoDto;
import todo.apitest.steps.CommonToDoSteps;

import java.math.BigInteger;
import java.util.HashMap;

import static todo.apitest.helper.data.generator.TestDataGenerator.createNewToDoId;
import static todo.apitest.helper.data.generator.ToDoObjectGenerator.getFilledRequestBodyForCreateNewToDo;
import static todo.apitest.helper.Preconditions.getFilledRequestSpecificationWithoutAuth;
import static todo.apitest.helper.Preconditions.getIdCreatedToDoItemWithTargetTaskTextLength;
import static todo.apitest.steps.ValidationHttpStatuses.validate200ResponseStatus;
import static todo.apitest.steps.ValidationHttpStatuses.validate400ResponseStatus;
import static todo.apitest.steps.ValidationHttpStatuses.validate404ResponseStatus;


@DisplayName("Verify the correct update of an existing task")
@Epic("CRUD Operations for ToDo Management")
@Feature("Update a ToDo Item")
public class UpdateExistingTaskTest extends CommonToDoSteps {

    @Test
    @Story("Update existing ToDo item")
    public void shouldReturnHttpStatusSuccessAndUpdateExistingToDoItem_whenUpdatingAllFieldsForToDo() {
        BigInteger idCreatedToDoItem = getIdCreatedToDoItemWithTargetTaskTextLength(50);
        ToDoDto requestBodyForUpdate = getFilledRequestBodyForCreateNewToDo(10);
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(requestBodyForUpdate, new HashMap<>(), new HashMap<>());

        Response response = putToDoRequest(requestSpecification, idCreatedToDoItem);

        validate200ResponseStatus(response);
        validateDoesNotItemExists(idCreatedToDoItem);
        validateRequestBodyEqualsItemFromList(requestBodyForUpdate.getId(), requestBodyForUpdate);
    }

    @Test
    @Story("Update existing ToDo item")
    public void shouldReturnHttpStatusSuccessAndUpdateExistingToDoItem_whenUpdatingOnlyTextField() {
        ToDoDto requestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification requestSpecificationForCreateItem = getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>());
        postToDoRequest(requestSpecificationForCreateItem);
        requestBody.setText("NEW TEXT");
        RequestSpecification requestSpecificationForUpdateItem = getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>());

        Response response = putToDoRequest(requestSpecificationForUpdateItem, requestBody.getId());

        validate200ResponseStatus(response);
        validateRequestBodyEqualsItemFromList(requestBody.getId(), requestBody);
    }

    @Test
    @Story("Update existing ToDo item")
    public void shouldReturnHttpStatusSuccessAndUpdateExistingToDoItem_whenUpdatingOnlyCompletedField() {
        ToDoDto requestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification requestSpecificationForCreateItem = getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>());
        postToDoRequest(requestSpecificationForCreateItem);
        requestBody.setCompleted(true);
        RequestSpecification requestSpecificationForUpdateItem = getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>());

        Response response = putToDoRequest(requestSpecificationForUpdateItem, requestBody.getId());

        validate200ResponseStatus(response);
        validateRequestBodyEqualsItemFromList(requestBody.getId(), requestBody);
    }

    @Test
    @Story("Update existing ToDo item")
    public void shouldReturnHttpStatusSuccessAndUpdateExistingToDoItem_whenUpdatingOnlyIdField() {
        ToDoDto requestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification requestSpecificationForCreateItem = getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>());
        postToDoRequest(requestSpecificationForCreateItem);
        ToDoDto requestBodyForUpdate = new ToDoDto();
        requestBodyForUpdate.setText(requestBody.getText());
        requestBodyForUpdate.setCompleted(requestBody.getCompleted());
        requestBodyForUpdate.setId(createNewToDoId());
        RequestSpecification requestSpecificationForUpdateItem = getFilledRequestSpecificationWithoutAuth(requestBodyForUpdate, new HashMap<>(), new HashMap<>());

        Response response = putToDoRequest(requestSpecificationForUpdateItem, requestBody.getId());

        validate200ResponseStatus(response);
        validateRequestBodyEqualsItemFromList(requestBodyForUpdate.getId(), requestBodyForUpdate);
    }

    @Test
    @Story("Error handling in case of incorrect ToDo update")
    public void shouldReturnHttpStatusBadRequest_whenUpdatingNonExistingToDo() {
        BigInteger nonExistingToDoId = createNewToDoId();
        ToDoDto requestBodyForUpdate = getFilledRequestBodyForCreateNewToDo(10);
        RequestSpecification requestSpecificationForUpdateItem = getFilledRequestSpecificationWithoutAuth(requestBodyForUpdate, new HashMap<>(), new HashMap<>());

        Response response = putToDoRequest(requestSpecificationForUpdateItem, nonExistingToDoId);

        validate404ResponseStatus(response);
    }

    @Test
    @Story("Update existing ToDo item")
    @Issue("This case creates duplicates based on the ToDoId")
    public void shouldReturnHttpStatusBadRequest_whenBodyForUpdateHasExistingToDoId() {
        ToDoDto firstRequestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification firstRequestSpecificationForCreateItem = getFilledRequestSpecificationWithoutAuth(firstRequestBody, new HashMap<>(), new HashMap<>());
        postToDoRequest(firstRequestSpecificationForCreateItem);
        ToDoDto secondRequestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification secondRequestSpecificationForCreateItem = getFilledRequestSpecificationWithoutAuth(secondRequestBody, new HashMap<>(), new HashMap<>());
        postToDoRequest(secondRequestSpecificationForCreateItem);
        RequestSpecification requestSpecificationForUpdateItem = getFilledRequestSpecificationWithoutAuth(firstRequestBody, new HashMap<>(), new HashMap<>());

        Response response = putToDoRequest(requestSpecificationForUpdateItem, secondRequestBody.getId());

        validate400ResponseStatus(response);
    }
}
