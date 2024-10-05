import { Customers } from '../db/DB.js';

export function saveCustomer(customer) {
    Customers.push(customer);
}

export function getAllCustomers() {

    return new Promise((resolve, reject) => {
        $.ajax({
            url: `http://localhost:8080/api/v1/customer/getAll`,
            method: "GET",
            dataType: "json",
            success: function (data) {
                if (!Array.isArray(data)) {
                    reject(new Error("Expected an array of customers"));
                    return;
                }

                let returnData = data.map((customer) => ({
                    custId: customer.customerId,
                    custName: customer.customerName,
                    custAddress: customer.customerAddress,
                    custSalary: customer.customerSalary
                }));

                console.log(returnData, "=============================================temp cust=");
                resolve(returnData);
            },
            error: function (error) {
                reject(error);
            }
        });
    });
}



export function updateCustomer(index , customer){
    Customers[index] = customer;
}

export function deleteCustomer(index){
    Customers.splice(index, 1);
}