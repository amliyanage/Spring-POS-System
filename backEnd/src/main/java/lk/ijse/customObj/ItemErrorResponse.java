package lk.ijse.customObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ItemErrorResponse implements ItemResponse {
    private int errorCode;
    private String errorMessage;
}
