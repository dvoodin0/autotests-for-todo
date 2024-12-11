package todo.apitest.helper.data.generator;

import todo.apitest.dto.ToDoDto;

import static todo.apitest.helper.data.generator.TestDataGenerator.createNewToDoId;
import static todo.apitest.helper.data.generator.TestDataGenerator.createRandomString;

public class ToDoObjectGenerator {

    public static ToDoDto getFilledRequestBodyForCreateNewToDo(int targetTaskTextLength) {
        ToDoDto body = new ToDoDto();
        body.setId(createNewToDoId());
        body.setText(createRandomString(targetTaskTextLength));
        body.setCompleted(false);
        return body;

    }
}
