package todo.apitest.tests.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import todo.apitest.steps.CommonToDoSteps;

import java.math.BigInteger;
import java.util.HashMap;

import static todo.apitest.helper.data.generator.TestDataGenerator.createNewToDoId;
import static todo.apitest.helper.Preconditions.getFilledRequestSpecificationWithAuth;
import static todo.apitest.helper.Preconditions.getFilledRequestSpecificationWithoutAuth;
import static todo.apitest.helper.Preconditions.getIdCreatedToDoItemWithTargetTaskTextLength;
import static todo.apitest.steps.ValidationHttpStatuses.validate204ResponseStatus;
import static todo.apitest.steps.ValidationHttpStatuses.validate401ResponseStatus;
import static todo.apitest.steps.ValidationHttpStatuses.validate404ResponseStatus;

@DisplayName("Verification of the DELETE method for existing tasks")
@Epic("CRUD Operations for ToDo Management")
@Feature("Delete existing a ToDo Item")
public class DeleteExistingTaskTest extends CommonToDoSteps {

    @Test
    @Story("Delete existing ToDo item")
    public void shouldReturnHttpStatusSuccessAndDeleteExistingToDoItem_whenAttemptToDeleteToDoByExistingId() {
        BigInteger idCreatedToDoItem = getIdCreatedToDoItemWithTargetTaskTextLength(50);
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithAuth(null, new HashMap<>(), new HashMap<>());

        Response response = deleteToDoRequest(requestSpecification, idCreatedToDoItem);

        validate204ResponseStatus(response);
        validateDoesNotItemExists(idCreatedToDoItem);

    }

    @Test
    @Story("Error handling in case of incorrect ToDo deletion")
    public void shouldReturnHttpStatusErrorAndNoDeleteExistingToDoItem_whenAttemptToDeleteToDoByDoesNotExistId() {
        BigInteger idCreatedToDoItem = getIdCreatedToDoItemWithTargetTaskTextLength(50);
        BigInteger doesNotExistToDoId = createNewToDoId();
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithAuth(null, new HashMap<>(), new HashMap<>());

        Response response = deleteToDoRequest(requestSpecification, doesNotExistToDoId);

        validate404ResponseStatus(response);
        validateItemExists(idCreatedToDoItem);

    }

    @Test
    @Story("Error handling in case of incorrect ToDo deletion")
    public void shouldReturnHttpStatusErrorAndNoDeleteExistingToDoItem_whenAttemptToDeleteWithoutAuthorization() {
        BigInteger idCreatedToDoItem = getIdCreatedToDoItemWithTargetTaskTextLength(50);
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithoutAuth(null, new HashMap<>(), new HashMap<>());

        Response response = deleteToDoRequest(requestSpecification, idCreatedToDoItem);

        validate401ResponseStatus(response);
        validateItemExists(idCreatedToDoItem);
    }

    @Test
    @Story("Error handling in case of incorrect ToDo deletion")
    public void shouldReturnHttpStatusErrorAndNoDeleteExistingToDoItem_whenAttemptToDeleteToDoWithIncorrectPassword() {
        BigInteger idCreatedToDoItem = getIdCreatedToDoItemWithTargetTaskTextLength(50);
        BigInteger doesNotExistToDoId = createNewToDoId();
        RequestSpecification requestSpecification = getFilledRequestSpecificationWithAuth(null, new HashMap<>(), new HashMap<>());
        requestSpecification.auth().preemptive().basic("admin", "test");

        Response response = deleteToDoRequest(requestSpecification, doesNotExistToDoId);

        validate401ResponseStatus(response);
        validateItemExists(idCreatedToDoItem);

    }
}
