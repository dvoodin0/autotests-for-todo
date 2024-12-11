package todo.apitest.dto;

import lombok.Data;

@Data
public class WebSocketMessageDto {
    private String type;
    private ToDoDto data;

}
