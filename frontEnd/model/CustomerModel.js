import { Customers } from '../db/DB.js';

export function saveCustomer(customer) {
    const endPoint = "http://localhost:8080/api/v1/customer"

    return new Promise((resolve, reject) =>{
        $.ajax({
            url: endPoint,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(customer),
            success: function(response , status, xhr){
                resolve({status: xhr.status, data: response})
            },
            error: function(xhr, status, error){
                reject(error)
            }
        })
    })
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

export function updateCustomer(customer){
    const endPoint ="http://localhost:8080/api/v1/customer"
    console.log("update",customer);

    return new Promise((resolve, reject) => {
        $.ajax({
            url: endPoint,
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(customer),
            success: function(response, status, xhr){
                resolve({status: xhr.status, data: response})
            },
            error: function(xhr, status, error){
                reject(error)
            }
        })
    })
}

export function deleteCustomer(customerId){
    const endPoint = "http://localhost:8080/api/v1/customer/"+customerId

    return new Promise((resolve, reject) => {
        $.ajax({
            url: endPoint,
            type: "DELETE",
            success: function (response , status, xhr) {
                resolve({status: xhr.status, data: response})
            },
            error: function (xhr, status, error) {
                reject(error)
            }
        })
    })
}