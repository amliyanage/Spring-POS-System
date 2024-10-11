package lk.ijse.customObj;

import lk.ijse.customObj.response.CustomerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerErrorResponse implements CustomerResponse {
    private int errorCode;
    private String errorMessage;
}
