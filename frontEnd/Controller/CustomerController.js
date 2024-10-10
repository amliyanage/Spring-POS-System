import { saveCustomer } from '../model/CustomerModel.js';
import { getAllCustomers } from '../model/CustomerModel.js';
import { updateCustomer } from '../model/CustomerModel.js';
import { deleteCustomer } from '../model/CustomerModel.js';

$(document).ready(function(){
    refresh();
});

document.querySelector('#CustomerManage #customerForm').addEventListener('submit', function(event){
    event.preventDefault();
});

var custId ;
var custName;
var custAddress;
var custSalary;

$('#CustomerManage .saveBtn').click( async function(){

    custId = $('#CustomerManage .custId').val();
    custName = $('#CustomerManage .custName').val();
    custAddress = $('#CustomerManage .custAddress').val();
    custSalary = $('#CustomerManage .custSalary').val();

    let customer = {
        customerId : custId,
        customerName : custName,
        customerAddress : custAddress,
        customerSalary : custSalary
    }

    let validResult = validate(customer);

    if(validResult){
        const response = await saveCustomer(customer);
        if(response.status === 201){
            alert('Customer Saved');
        }
        else{
            alert(response.data);
        }
        refresh();
    }

});


async function validate(customer){

    let valid = true;

    if((/^C0[0-9]+$/).test(customer.customerId)){
        $('#CustomerManage .invalidCustId').text('');
        valid = true;
    }
    else{
        $('#CustomerManage .invalidCustId').text('Invalid Customer Id');
        valid = false;
    }

    if((/^(?:[A-Z][a-z]*)(?: [A-Z][a-z]*)*$/).test(customer.customerName)){
        $('#CustomerManage .invalidCustName').text('');
        
        if(valid){
            valid = true;
        }
    }

    else{
        $('#CustomerManage .invalidCustName').text('Invalid Customer Name');
        valid = false;
    }

    if((/^[A-Z][a-z, ]+$/).test(customer.customerAddress)){
        $('#CustomerManage .invalidCustAddress').text('');
        
        if(valid){
            valid = true;
        }
    }

    else{
        $('#CustomerManage .invalidCustAddress').text('Invalid Customer Address');
        valid = false;
    }

    if(customer.customerSalary != null && customer.customerSalary > 0){
        $('#CustomerManage .invalidCustSalary').text('');
        if(valid){
            valid = true;
        }
    }
    
    else{
        $('#CustomerManage .invalidCustSalary').text('Invalid Customer Salary');
        valid = false;
    }

    let customers = await getAllCustomers();
    for(let i = 0; i < customers.length; i++){
        if(customers[i].customerId === customer.customerId){
            $('#CustomerManage .invalidCustId').text('Customer Id Already Exists');
            valid = false;
        }
    }

    return valid;
}

function loadTable(customer){
    $('#CustomerManage .tableRow').append(
        '<tr> ' +
            '<td>' + customer.customerId + '</td>' +
            '<td>' + customer.customerName + '</td>' +
            '<td>' + customer.customerAddress + '</td>' +
            '<td>' + customer.customerSalary + '</td>' +
        '</tr>' 
    );
}

function extractNumber(id) {
    var match = id.match(/C0(\d+)/);
    if (match && match.length > 1) {
        return parseInt(match[1]);
    }
    return null;
}

async function createCustomerId() {
    let customers = await getAllCustomers();
    
    if (!customers || customers.length === 0) {
        return 'C01';
    } else {
        let lastCustomer = customers[customers.length - 1];
        console.log("lastCustomer",lastCustomer);
        let id = lastCustomer && lastCustomer.customerId ? lastCustomer.customerId : 'C00';
        console.log("id",id);
        let number = extractNumber(id);
        number++;
        console.log("number",number);
        $('#CustomerManage .custId').val("C0" + number);
    }
}

function refresh(){
    createCustomerId();
    $('#CustomerManage .custName').val('');
    $('#CustomerManage .custAddress').val('');
    $('#CustomerManage .custSalary').val('');
    $('#CustomerManage .invalidCustId').text('');
    $('#CustomerManage .invalidCustName').text('');
    $('#CustomerManage .invalidCustAddress').text('');
    $('.counts .customers h2').text(getAllCustomers().length);
    reloadTable();
}

$('#CustomerManage .cleatBtn').click(function(){
    refresh();
});

$('#CustomerManage .searchBtn').click(function(){
    searchCustomer($('#CustomerManage .custId').val());
});

async function searchCustomer(id){
    let customers = await getAllCustomers();
    let customer = customers.find(c => c.customerId === id);

    if(customer){
        $('#CustomerManage .custName').val(customer.customerName);
        $('#CustomerManage .custAddress').val(customer.customerAddress);
        $('#CustomerManage .custSalary').val(customer.customerSalary);
    }
    else{
        alert('Customer Not Found');
    }
}

$('#CustomerManage .updateBtn').click( async function(){
    
    let UpdateCustomer = {
        customerId : "C00",
        customerName : $('#CustomerManage .custName').val(),
        customerAddress : $('#CustomerManage .custAddress').val(),
        customerSalary : $('#CustomerManage .custSalary').val()
    }

    let validResult = validate(UpdateCustomer);

    UpdateCustomer.customerId = $('#CustomerManage .custId').val();
    
    if(validResult){
        const response = await updateCustomer(UpdateCustomer);
        if(response.status === 201){
            alert('Customer Updated');
            refresh();
        }
        else{
            alert(response.data);
        }
    }

});

async function reloadTable(){
    let customers = await getAllCustomers();
    console.log("contoleler",customers);
    $('#CustomerManage .tableRow').empty();
    customers.forEach(c => {
        loadTable(c);
    });
}

$('#CustomerManage .removeBtn').click( async function(){

    let id = $('#CustomerManage .custId').val();

    const response = await deleteCustomer(id)

    if(response.status === 200){
        alert("delete success");
        refresh()
    }
    else{
        alert(response.data)
    }

});

$('#CustomerManage .tableRow').on('click', 'tr', function(){
    let id = $(this).children('td:eq(0)').text();
    let name = $(this).children('td:eq(1)').text();
    let qty = $(this).children('td:eq(2)').text();
    let price = $(this).children('td:eq(3)').text();

    $('#CustomerManage .custId').val(id);
    $('#CustomerManage .custName').val(name);
    $('#CustomerManage .custAddress').val(qty);
    $('#CustomerManage .custSalary').val(price);
});

