import { Items } from '../db/DB.js';

export function saveItem(item) {
    const end_point = "http://localhost:8080/api/v1/item"
    return new Promise((resolve, reject) => {
        $.ajax({
            url: end_point,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(item),
            success: function(response, status, xhr) {
                resolve({status: xhr.status, data: response})
            },
            error: function(xhr, status, error) {
                reject(error)
            }
        })
    })
}

export function getAllItems() {
    const end_point = "http://localhost:8080/api/v1/item/getAll"
    return new Promise((resolve, reject) => {
        $.ajax({
            url: end_point,
            type: "GET",
            success: function(response) {
                resolve(response)
            },
            error: function(xhr, status, error) {
                reject(error)
            }
        })
    })
}

export function deleteItem(index){
    Items.splice(index, 1);
}

export function updateItem(index, item){
    Items[index] = item;
}