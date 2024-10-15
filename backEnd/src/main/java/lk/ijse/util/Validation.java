package lk.ijse.util;


import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;

public class Validation {

    public static String validateCustomer(CustomerDTO customerDTO){

        if (customerDTO.getCustomerId() == null || !customerDTO.getCustomerId().matches("C\\d+")){
            return "Invalid";
        }

        if (customerDTO.getCustomerName() == null || !customerDTO.getCustomerName().matches("[A-Za-z. ]+")){
            return "Invalid";
        }

        if (customerDTO.getCustomerSalary() <= 0) {
            return "Invalid";
        }

        if (customerDTO.getCustomerAddress() == null){
            return "Invalid";
        }

        return "Valid";

    }

    public static String validateItem(ItemDTO itemDTO){

        if (itemDTO.getItemCode() == null || !itemDTO.getItemCode().matches("I\\d+")){
            return "Invalid";
        }

        if (itemDTO.getItemName() == null || !itemDTO.getItemName().matches("[A-Za-z. ]+")){
            return "Invalid";
        }

        if (itemDTO.getItemPrice() <= 0) {
            return "Invalid";
        }

        if (itemDTO.getItemQty() <= 0) {
            return "Invalid";
        }

        return "Valid";

    }

}
