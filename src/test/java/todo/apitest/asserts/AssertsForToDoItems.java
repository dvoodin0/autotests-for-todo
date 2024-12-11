package todo.apitest.asserts;

import todo.apitest.dto.ToDoDto;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static todo.apitest.helper.Preconditions.getTodoItemFromListById;

public class AssertsForToDoItems {

    public static void assertItemDoesNotExistAfterDelete(BigInteger toDoId) {
        assertThat(getTodoItemFromListById(toDoId)).isEmpty();
    }

    public static void assertItemExist(BigInteger toDoId) {
        assertThat(getTodoItemFromListById(toDoId)).isNotEmpty();
    }

    public static void assertRequestBodyEqualsItemFromList(BigInteger toDoId, ToDoDto toDoItem) {
        Optional<ToDoDto> toDoItemFromList = getTodoItemFromListById(toDoId);
        assertThat(toDoItemFromList.get())
                .usingRecursiveAssertion()
                .isEqualTo(toDoItem);
    }

    public static void assertItemListEqualsGotList(List<ToDoDto> expectedList, List<ToDoDto> actualList) {
        assertThat(expectedList)
                .usingRecursiveComparison()
                .isEqualTo(actualList);
    }

    public static <T> void assertExpectedObjectEqualsActualObjectWithoutIgnoringFields(T expected, T actual) {
        assertThat(expected)
                .usingRecursiveComparison()
                .isEqualTo(actual);
    }

    public static <T> void assertExpectedFieldValueEqualsActualFieldValue(T expected, T actual) {
        assertThat(expected).isEqualTo(actual);
    }

    public static <T> void assertObjectIsEmpty(T object) {
        assertThat(object).isNull();
    }
}
