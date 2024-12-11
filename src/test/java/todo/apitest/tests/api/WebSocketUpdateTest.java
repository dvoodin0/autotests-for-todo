package todo.apitest.tests.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import todo.apitest.dto.ToDoDto;
import todo.apitest.dto.WebSocketMessageDto;

import java.util.HashMap;


import static todo.apitest.asserts.AssertsForToDoItems.assertObjectIsEmpty;
import static todo.apitest.constants.Properties.WEB_SOCKET_EVENT_TYPE;
import static todo.apitest.helper.Preconditions.getFilledRequestSpecificationWithAuth;
import static todo.apitest.helper.Preconditions.getFilledRequestSpecificationWithoutAuth;
import static todo.apitest.helper.data.generator.ToDoObjectGenerator.getFilledRequestBodyForCreateNewToDo;
import static todo.apitest.steps.CommonToDoSteps.deleteToDoRequest;
import static todo.apitest.steps.CommonToDoSteps.postToDoRequest;
import static todo.apitest.steps.CommonToDoSteps.putToDoRequest;
import static todo.apitest.steps.CommonToDoSteps.validateExpectedFieldValueEqualsActualFieldValue;
import static todo.apitest.steps.CommonToDoSteps.validateExpectedObjectEqualsActualObjectWithoutIgnoringFields;
import static todo.apitest.steps.WebSocketSteps.clearReceivedMessage;
import static todo.apitest.steps.WebSocketSteps.connectToWebSocketServer;
import static todo.apitest.steps.WebSocketSteps.disconnectFromWebSocket;
import static todo.apitest.steps.WebSocketSteps.getReceivedMessage;
import static todo.apitest.steps.WebSocketSteps.waitForMessage;

@DisplayName("WebSocket Event Handling for ToDo Items")
@Epic("WebSocket Event Handling for ToDo ")
@Feature("Real-Time WebSocket Updates for ToDo")
public class WebSocketUpdateTest {

    @BeforeEach
    public void setup() throws Exception {
        clearReceivedMessage();
        connectToWebSocketServer(1);
    }

    @Test
    @Story("WebSocket Client Should Receive Message After Create Requests")
    public void shouldReceiveValidMessage_whenPostRequestIsSentToCreateNewTask() throws Exception {
        ToDoDto requestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>());
        postToDoRequest(requestSpecification);

        waitForMessage();
        WebSocketMessageDto receivedMessage = new ObjectMapper().readValue(getReceivedMessage(), WebSocketMessageDto.class);

        validateExpectedObjectEqualsActualObjectWithoutIgnoringFields(receivedMessage.getData(), requestBody);
        validateExpectedFieldValueEqualsActualFieldValue(receivedMessage.getType(), WEB_SOCKET_EVENT_TYPE);


    }


    @Test
    @Story("WebSocket Client Should Not Receive Messages After Non-Create Requests")
    public void shouldNotReceiveMessage_whenPutRequestIsSentToCreateNewTask() throws Exception {
        ToDoDto requestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>());
        postToDoRequest(requestSpecification);
        waitForMessage();
        getReceivedMessage();
        clearReceivedMessage();
        ToDoDto requestBodyForUpdate = getFilledRequestBodyForCreateNewToDo(10);
        RequestSpecification requestSpecificationForUpdate = getFilledRequestSpecificationWithoutAuth(requestBodyForUpdate, new HashMap<>(), new HashMap<>());

        putToDoRequest(requestSpecificationForUpdate, requestBody.getId());
        waitForMessage();
        String messageAfterUpdate = getReceivedMessage();

        assertObjectIsEmpty(messageAfterUpdate);

    }

    @Test
    @Story("WebSocket Client Should Not Receive Messages After Non-Create Requests")
    public void shouldNotReceiveMessage_whenDeleteRequestIsSentToCreateNewTask() throws Exception {
        ToDoDto requestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(requestBody, new HashMap<>(), new HashMap<>());
        postToDoRequest(requestSpecification);
        waitForMessage();
        getReceivedMessage();
        clearReceivedMessage();
        RequestSpecification requestSpecificationForDelete = getFilledRequestSpecificationWithAuth(null, new HashMap<>(), new HashMap<>());

        deleteToDoRequest(requestSpecificationForDelete, requestBody.getId());
        waitForMessage();
        String messageAfterUpdate = getReceivedMessage();

        assertObjectIsEmpty(messageAfterUpdate);

    }

    @Test
    @Story("WebSocket Client Should Receive Message After Create Requests")
    public void shouldReceiveMessageTwoMessages_whenReconnectAfterDisconnect() throws Exception {
        ToDoDto firstRequestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification requestSpecificationForFirstRequest = getFilledRequestSpecificationWithoutAuth(firstRequestBody, new HashMap<>(), new HashMap<>());
        ToDoDto secondRequestBody = getFilledRequestBodyForCreateNewToDo(5);
        RequestSpecification requestSpecificationForSecondRequest = getFilledRequestSpecificationWithoutAuth(secondRequestBody, new HashMap<>(), new HashMap<>());
        postToDoRequest(requestSpecificationForFirstRequest);
        waitForMessage();
        WebSocketMessageDto firstReceivedMessage = new ObjectMapper().readValue(getReceivedMessage(), WebSocketMessageDto.class);

        disconnectFromWebSocket();
        connectToWebSocketServer(1);
        postToDoRequest(requestSpecificationForSecondRequest);
        waitForMessage();
        WebSocketMessageDto secondReceivedMessage = new ObjectMapper().readValue(getReceivedMessage(), WebSocketMessageDto.class);

        validateExpectedObjectEqualsActualObjectWithoutIgnoringFields(firstReceivedMessage.getData(), firstRequestBody);
        validateExpectedFieldValueEqualsActualFieldValue(firstReceivedMessage.getType(), WEB_SOCKET_EVENT_TYPE);
        validateExpectedObjectEqualsActualObjectWithoutIgnoringFields(secondReceivedMessage.getData(), secondRequestBody);
        validateExpectedFieldValueEqualsActualFieldValue(secondReceivedMessage.getType(), WEB_SOCKET_EVENT_TYPE);


    }

    @AfterEach
    public void cleanup() {
        disconnectFromWebSocket();
    }

}
