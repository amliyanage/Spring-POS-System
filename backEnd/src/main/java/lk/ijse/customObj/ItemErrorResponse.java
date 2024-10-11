package lk.ijse.customObj;

import lk.ijse.customObj.response.ItemResponse;
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
