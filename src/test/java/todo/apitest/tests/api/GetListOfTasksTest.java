package todo.apitest.tests.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import todo.apitest.dto.ToDoDto;
import todo.apitest.steps.CommonToDoSteps;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static todo.apitest.helper.Preconditions.generateSpecifiedNumberOfTodos;
import static todo.apitest.helper.Preconditions.getAllExistingTodos;
import static todo.apitest.helper.Preconditions.getFilledQueryParametersForGetList;
import static todo.apitest.helper.Preconditions.getFilledRequestSpecificationWithoutAuth;
import static todo.apitest.steps.ValidationHttpStatuses.validate200ResponseStatus;

@DisplayName("Verify the functionality of the method for getting the task list")
@Epic("CRUD Operations for ToDo Management")
@Feature("Get ToDo Items list")
public class GetListOfTasksTest extends CommonToDoSteps {

    @Test
    @Story("Get list of ToDo Items")
    public void shouldReturnHttpStatusSuccessAndGetFullListOfToDoItem_whenAttemptToGetWithoutOffsetAndLimit() {
        int toDoCount = 2;
        int limitAndOffsetBypassCondition = -1;
        generateSpecifiedNumberOfTodos(toDoCount);
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(null, new HashMap<>(), getFilledQueryParametersForGetList(limitAndOffsetBypassCondition, limitAndOffsetBypassCondition));

        Response response = getListOfToDoRequest(requestSpecification);

        validate200ResponseStatus(response);

    }

    @Test
    @Story("Get ToDo list with specified limit and offset values")
    public void shouldReturnHttpStatusSuccessAndGetEmptyListOfToDoItem_whenAttemptToGetWithZeroOffsetAndZeroLimit() {
        int offset = 0;
        int limit = 0;
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(null, new HashMap<>(), getFilledQueryParametersForGetList(limit, offset));

        Response response = getListOfToDoRequest(requestSpecification);
        List<ToDoDto> listOfToDo = response.jsonPath().getList("", ToDoDto.class);

        assertThat(listOfToDo.size()).isEqualTo(0);

    }

    @Test
    @Story("Get ToDo list with specified limit and offset values")
    public void shouldReturnHttpStatusSuccessAndGetCorrectListOfToDoItem_whenAttemptToGetWithZeroOffsetAndFilledLimit() {
        int toDoCount = 10;
        int limit = 5;
        generateSpecifiedNumberOfTodos(toDoCount);
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(null, new HashMap<>(), getFilledQueryParametersForGetList(limit, 0));

        Response response = getListOfToDoRequest(requestSpecification);
        List<ToDoDto> listOfToDo = response.jsonPath().getList("", ToDoDto.class);

        assertThat(listOfToDo.size()).isEqualTo(limit);

    }

    @Test
    @Story("Get ToDo list with specified limit and offset values")
    public void shouldReturnHttpStatusSuccessAndGetCorrectListOfToDoItem_whenAttemptToGetWithFilledOffsetAndLimit() {
        int offset = getAllExistingTodos().size();
        int toDoCount = 2;
        List<ToDoDto> expectedListOfNewToDo = generateSpecifiedNumberOfTodos(toDoCount);
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(null, new HashMap<>(), getFilledQueryParametersForGetList(toDoCount, offset));

        Response response = getListOfToDoRequest(requestSpecification);
        List<ToDoDto> listOfToDo = response.jsonPath().getList("", ToDoDto.class);

        validateGotItemListEqualsExpectedItemList(expectedListOfNewToDo, listOfToDo);
    }

}
