package todo.apitest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class ToDoDto {
    private BigInteger id;
    private String text;
    private Boolean completed;
}
