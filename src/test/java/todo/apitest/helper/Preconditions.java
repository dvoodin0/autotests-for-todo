package todo.apitest.helper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import todo.apitest.dto.ToDoDto;
import todo.apitest.steps.CommonToDoSteps;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static todo.apitest.constants.Properties.LIMIT_QUERY_PARAM_NAME;
import static todo.apitest.constants.Properties.OFFSET_QUERY_PARAM_NAME;
import static todo.apitest.helper.AuthSchemeConfiguration.getBasicAuthScheme;
import static todo.apitest.helper.data.generator.ToDoObjectGenerator.getFilledRequestBodyForCreateNewToDo;


public class Preconditions extends CommonToDoSteps {
    public static RequestSpecification getFilledRequestSpecificationWithoutAuth(Object body, Map<String, String> headers, Map<String, ?> queryParameters) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeaders(headers)
                .addQueryParams(queryParameters)
                .build();
        return body == null ? requestSpecification : requestSpecification.body(body);
    }

    public static RequestSpecification getFilledRequestSpecificationWithAuth(Object body, Map<String, String> headers, Map<String, ?> queryParameters) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeaders(headers)
                .addQueryParams(queryParameters)
                .setAuth(getBasicAuthScheme())
                .build();
        return body == null ? requestSpecification : requestSpecification.body(body);
    }

    public static BigInteger getIdCreatedToDoItemWithTargetTaskTextLength(int targetTaskTextLength) {
        ToDoDto requestBody = getFilledRequestBodyForCreateNewToDo(targetTaskTextLength);
        postToDoRequest(getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>()));
        return requestBody.getId();
    }

    public static List<ToDoDto> generateSpecifiedNumberOfTodos(int count) {
        List<ToDoDto> listToDoItems = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ToDoDto requestBody = getFilledRequestBodyForCreateNewToDo(25);
            listToDoItems.add(requestBody);
            postToDoRequest(getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>()));
        }
        return listToDoItems;
    }

    public static Map<String, ?> getFilledQueryParametersForGetList(int limit, int offset) {
        Map queryParams = new HashMap<>();
        if (limit >= 0) {
            queryParams.put(LIMIT_QUERY_PARAM_NAME, limit);
        }
        if (offset >= 0) {
            queryParams.put(OFFSET_QUERY_PARAM_NAME, offset);
        }
        return queryParams;
    }

    public static List<ToDoDto> getAllExistingTodos() {
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(null, new HashMap<>(), getFilledQueryParametersForGetList(-1, -1));
        List<ToDoDto> listOfToDo = getListOfToDoRequest(requestSpecification).jsonPath().getList("", ToDoDto.class);
        return listOfToDo;
    }

    public static Optional<ToDoDto> getTodoItemFromListById(BigInteger toDoId) {
        List<ToDoDto> listOfToDo = getAllExistingTodos();
        return listOfToDo.stream().filter(it -> it.getId().equals(toDoId)).findFirst();
    }
}
