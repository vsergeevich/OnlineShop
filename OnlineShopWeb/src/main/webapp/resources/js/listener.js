
$(document).ready(function () {
    $('#search').submit(function (event) {
        var searchStr = $('#searchStr').val();
        $.ajax({
            url: $("#search").attr("action"),
            data: JSON.stringify(searchStr),
            type: 'POST',
            dataType: 'text',
            contentType: 'application/json',
            success: function (string) {
                $("#testdiv").html(string);
            },
            error: function (jqXHR) {
                $("#testdiv").html('<H1>Данные не получены</H1>');
                console.info("error: " + jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
        event.preventDefault();
    });
//    добавление товара
    $('#orderProduct').submit(function (event) {
        var productID = $("#orderProduct").attr("product");
        var fio = document.getElementById('fio').value;
        var mail = document.getElementById('mail').value;
        var phone = document.getElementById('phone').value;
        var adress = document.getElementById('adress').value;
        var data = {productID: productID, fio: fio, mail: mail, phone: phone, adress: adress};
        $.ajax({
            url: "/WebShop/buy.htm",
            data: data,
            type: 'GET',
            dataType: 'text',
            success: function () {
                $('#orderModal').modal('hide');
                $("#cartItems").remove();
                document.getElementById("orderProduct").reset();
            },
            error: function (jqXHR) {
                console.info("error: " + jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
        event.preventDefault();
    });

});

$('#manPanel').submit(function (event) {
    var bool = $('form input[type=radio]:checked').val();
    var catId = $("#currentCategory").attr("itemprop");
    $.ajax({
        url: $("#manPanel").attr("action") + "/" + bool,
        type: 'GET',
        data: {"page": 1, "catId": catId},
        dataType: 'text',
        success: function (res) {
            $("#testdiv").html(res);
        },
        error: function (jqXHR) {
            $("#testdiv").html('<H1>Данные не получены</H1>');
            console.info("error: " + jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
    event.preventDefault();
});


function showMoreGoods(page) {
    var url = $("#moreprod").attr("itemprop");
    var catId = $("#currentCategory").attr("itemprop");
    $("#moreprod").remove();
    $.ajax({
        url: url,
        data: {"page": page, "catId": catId},
        type: 'GET',
        dataType: 'text',
        success: function (res) {
            var div = document.getElementById('container');
            div.innerHTML = div.innerHTML + res;
        },
        error: function (jqXHR) {

            alert(url);
            alert(page + "error: " + jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
    event.preventDefault();
}

function addToCart(id) {
    $.ajax({
        url: "/WebShop/cart.htm",
        data: {"id": id},
        type: 'POST',
        success: function () {
            alert("Товар успешно добавлен в корзину");
        },
        error: function () {
            alert("bad");
        }
    });
    event.preventDefault();
}


function orderModal() {
    $.ajax({
        url: "/WebShop/showcart.htm",
        type: 'GET',
        dataType: 'text',
        success: function (rez) {
            $("#cartItems").html(rez);
            $('#orderModal').modal();
        },
        error: function (rez) {
            $("#cartItems").html(rez);
            $('#orderModal').modal();
        }
    });
    event.preventDefault();
}


