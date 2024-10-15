package lk.ijse.util;


import lk.ijse.dto.CustomerDTO;

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

}
