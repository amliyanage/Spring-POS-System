import { Orders } from "../db/DB.js";

export function getAllOrders() {
    const end_point = "http://localhost:8080/api/v1/order"
    return new Promise((resolve , Reject) =>{
        $.ajax({
            url: end_point,
            type: "GET",
            success: function(response){
                resolve(response);
            },
            error: function(xhr, status, error){
                Reject(error);
            }
        })  
    })
}

export function saveOrder(order){
    console.log("send Order : ",order)
    const end_point = "http://localhost:8080/api/v1/order"
    return new Promise((resolve , Reject) =>{
        $.ajax({
            url: end_point,
            type: "POST",
            data: JSON.stringify(order),
            contentType: "application/json",
            success: function(response, status, xhr){
                resolve({status: xhr.status, data: response});
            },
            error: function(xhr, status, error){
                Reject(error);
            }
        })  
    })
}