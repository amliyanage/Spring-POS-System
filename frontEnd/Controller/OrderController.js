import { getAllOrders } from "../model/OrderModel.js";
import { getAllCustomers } from "../model/CustomerModel.js";
import { getAllItems, updateItem } from "../model/ItemModel.js";
import { saveOrder } from "../model/OrderModel.js";

var itemId;
var itemQty;
var orderQty;

$(document).ready(function () {
    refresh();
});

$('.orderManageBtn').click(function(){
    refresh();
});

async function refresh(){
    generateId();
    $('#OrderManage .orderDate').val(new Date().toISOString().split('T')[0]);
    loadCustomer();
    loadItems();
    $('#OrderManage .Total').text("");
    $('#OrderManage .Balance').val("");
    $('#OrderManage .Cash').val('');
    $('#OrderManage .Discount').val('');
    const orders = await getAllOrders();
    $('.counts .orders h2').text(orders.length);
}

function extractNumber(id){
    var match = id.match(/OD(\d+)/);
    if(match && match.length > 1){
        return match[1];
    }
    return null;
}

async function generateId(){
    let orders = await getAllOrders();

    // alert(orders.length);
    
    if(orders.length === 0){
        $('#OrderManage .orderId').val('OD01');
    }
    else{
        // alert('awa');
        let orderId = orders[orders.length - 1].orderId;
        console.log("oder Id : ",orderId);
        let number = extractNumber(orderId);
        number++;
        console.log("new oder Id : OD",number);
        // alert('OD0' + number);
        $('#OrderManage .orderId').val('OD0' + number);
    }
}

async function loadCustomer(){
    let cmb = $('#OrderManage .customers');
    cmb.empty();
    let option = [];
    let customers = await getAllCustomers();
    option.unshift('');
    for (let i = 0; i < customers.length; i++) {
        option.push(customers[i].customerId);
    }

    $.each(option, function (index, value) {
        cmb.append($('<option>').val(value).text(value));
    });
}

$('#OrderManage .customers').change(async function(){
    let customers = await getAllCustomers();
    let customer = customers.find(c => c.customerId === $(this).val());
    $('#OrderManage .custId').val(customer.customerId);
    $('#OrderManage .custName').val(customer.customerName);
    $('#OrderManage .custAddress').val(customer.customerAddress);
    $('#OrderManage .custSalary').val(customer.customerSalary);
});

async function loadItems(){
    let cmb = $('#OrderManage .itemCmb');
    cmb.empty();
    let option = [];
    let items = await getAllItems();

    for (let i = 0; i < items.length; i++) {
        option.push(items[i].itemCode);
    }

    option.unshift('');

    $.each(option, function (index, value) {
        cmb.append($('<option>').val(value).text(value));
    });
}

$('#OrderManage .itemCmb').change(async function(){
    let item = await getAllItems();
    item = item.find(I => I.itemCode === $(this).val());
    itemId = item.itemCode;
    // alert(item.itemQty);
    itemQty = item.itemQty;
    $('#OrderManage .addBtn').text('Add');
    $('#OrderManage .itemCode').val(item.itemCode);
    $('#OrderManage .itemName').val(item.itemName);
    $('#OrderManage .itemQty').val(item.itemQty);
    $('#OrderManage .itemPrice').val(item.itemPrice);
});

let getItems = [];

function clear(tableCount){
    if(tableCount === 1){
        $('#OrderManage .itemCode').val('');
        $('#OrderManage .itemName').val('');
        $('#OrderManage .itemPrice').val('');
        $('#OrderManage .itemQty').val('');
        $('#OrderManage .orderQty').val('');
        $('#OrderManage .SubTotal').text('');
        $('#OrderManage .Cash').val('');
        $('#OrderManage .Total').text('');
        $('#OrderManage .Discount').val('');
        $('#OrderManage .itemCmb').val('');

    }
    else{
        $('#OrderManage .custId').val('');
        $('#OrderManage .custName').val('');
        $('#OrderManage .custAddress').val('');
        $('#OrderManage .custSalary').val('');
        $('#OrderManage .itemCode').val('');
        $('#OrderManage .itemName').val('');
        $('#OrderManage .itemPrice').val('');
        $('#OrderManage .itemQty').val('');
        $('#OrderManage .orderQty').val('');
    }
}

$('#OrderManage .addBtn').click(function(){

    if($('#OrderManage .addBtn').text() === 'delete'){
        dropItem();
    }

    else{
        let getItem = {
            itemCode: $('#OrderManage .itemCode').val(),
            getItems: $('#OrderManage .itemName').val(),
            itemPrice: parseFloat($('#OrderManage .itemPrice').val()),
            itemQty: parseInt($('#OrderManage .orderQty').val(), 10),
            total: parseFloat($('#OrderManage .itemPrice').val()) * parseInt($('#OrderManage .orderQty').val(), 10)
        };
    
        let itemQty = parseInt($('#OrderManage .itemQty').val(), 10);
        let orderQty = parseInt($('#OrderManage .orderQty').val(), 10);
    
        if(itemQty >= orderQty){
            if($('#OrderManage .custId').val() !== '' && $('#OrderManage .custName').val() !== null){
                if(orderQty > 0){
                    let item = getItems.find(I => I.itemCode === getItem.itemCode);
                    if(item == null){
                        getItems.push(getItem);
                        loadTable();
                        clear(1);
                        setTotal();
                    }
                    else{
                        alert('Already Added');
                    }
                } else {
                    alert('Invalid Quantity');
                }
            } else { 
                alert('Invalid Customer');
            }
        } else {
            alert('Not Enough Quantity');
        }
    }
});



function loadTable(){
    $('#OrderManage .tableRows').empty();
    for(let i = 0; i < getItems.length; i++){
        $('#OrderManage .tableRows').append(
            '<div> ' +
                '<div>' + getItems[i].itemCode + '</div>' +
                '<div>' + getItems[i].getItems + '</div>' +
                '<div>' + getItems[i].itemPrice + '</div>' +
                '<div>' + getItems[i].itemQty + '</div>' +
                '<div>' + getItems[i].total + '</div>' +
            '</tr>' 
        );
    }
}

function setTotal(){
    let total = 0;
    for(let i = 0; i < getItems.length; i++){
        total += getItems[i].total;
    }
    $('#OrderManage .Total').text(total);
}

$('#OrderManage .placeOrder').click(async function(){
    let cash = parseFloat($('#OrderManage .Cash').val());
    let total = parseFloat($('#OrderManage .Total').text());
    let discount = parseFloat($('#OrderManage .Discount').val());

    // alert(cash + ' ' + total + ' ' + discount);

    if(cash >= total){
        if(discount >= 0 && discount <= 100){
            let subTotal = total - (total * discount / 100);
            $('#OrderManage .SubTotal').text(subTotal.toFixed(2));
            let balance = cash - subTotal;
            $('#OrderManage .Balance').val(balance.toFixed(2));

            let getAllItems = [];

            getItems.forEach(item => {
                let newItem = {
                    itemCode : item.itemCode,
                    itemName : item.getItems,
                    itemPrice : item.itemPrice,
                    itemQty : item.itemQty,
                }
                getAllItems.push(newItem);
            });

            let Order = {
                orderId : $('#OrderManage .orderId').val(),
                balance : balance,
                date : $('#OrderManage .orderDate').val(),
                discount : discount,
                total : total,
                subTotal : subTotal,
                customer : {
                    customerId : $('#OrderManage .custId').val(),
                    customerName : $('#OrderManage .custName').val(),
                    customerAddress : $('#OrderManage .custAddress').val(),
                    customerSalary : $('#OrderManage .custSalary').val()
                },
                items : getAllItems,
            }

            const response = await saveOrder(Order);
            if(response.status === 201){
                alert('Order Placed');
                clear(2);
                refresh();
                getItems = [];
                loadTable();
            }
            else{
                alert(response.data);
            }
            
        } else {
            alert('Invalid Discount');
        }
    } else {
        alert('Not Enough Cash');
    }
});


// function updateItemData(){
//     let items = getAllItems();
//     for(let i = 0; i < getItems.length; i++){
//         let item = items.find(I => I.itemId === getItems[i].itemCode);
//         item.itemQty -= getItems[i].itemQty;
//         let index = items.findIndex(I => I.itemId === getItems[i].itemCode);
//         updateItem(index, item);
//     }
// }

$('.mainTable .tableRows').on('click', 'div', function(){
    let itemCode = $(this).children('div:eq(0)').text();
    let itemName = $(this).children('div:eq(1)').text();
    let price = $(this).children('div:eq(2)').text();
    let qty = $(this).children('div:eq(3)').text();

    $('#OrderManage .itemCode').val(itemCode);
    $('#OrderManage .itemName').val(itemName);
    $('#OrderManage .itemPrice').val(price);
    $('#OrderManage .orderQty').val(qty);

    $('#OrderManage .ItemSelect .addBtn').text('delete');
    $('#OrderManage .ItemSelect .addBtn').css('background-color', 'red');
});

function dropItem(){
    let itemCode = $('#OrderManage .itemCode').val();
    let item = getItems.find(I => I.itemCode === itemCode);
    let index = getItems.findIndex(I => I.itemCode === itemCode);
    getItems.splice(index, 1);
    alert('Item Removed');
    loadTable();
    clear(1);
    setTotal();
}


// $('#orderManage .itemCmb')