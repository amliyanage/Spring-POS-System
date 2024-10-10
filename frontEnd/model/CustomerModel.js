import { Customers } from '../db/DB.js';

export function saveCustomer(customer) {
    Customers.push(customer);
}

export function getAllCustomers() {
    const endPoint = "http://localhost:8080/api/v1/customer/getAll"

    return new Promise((resolve, reject) => {
        $.ajax({
            url: endPoint,
            type: "GET",
            success: function (response) {
                resolve(response)
            },
            error: function (xhr, status, error) {
                reject(error)
            }
        })
    })
}

export function updateCustomer(index , customer){
    Customers[index] = customer;
}

export function deleteCustomer(index){
    Customers.splice(index, 1);
}