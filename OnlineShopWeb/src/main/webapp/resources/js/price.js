$(function () {
    $("#slider-range").slider({
        range: true,
        min: 0,
        max: 25000,
        values: [0, 25000],
        slide: function (event, ui) {
            $("#amount").val("" + ui.values[ 0 ] + " - " + ui.values[ 1 ]);
        },
        change: function (event, ui) {
             var catId = $("#currentCategory").attr("itemprop");
            $.ajax({
                url: "/WebShop/price/" + ui.values[ 0 ] + "/" + ui.values[ 1 ] + "/?page=1",
                type: 'GET',
                 data: {"catId": catId},
                dataType: 'text',
                success: function (res) {
                    $("input:radio").removeAttr("checked");
                    $("#testdiv").html(res);
                },
                error: function (jqXHR) {
                    $("#testdiv").html('<H1>Данные не получены</H1>');
                    console.info("error: " + jqXHR.status + ' ' + jqXHR.responseText);
                }
            });
        }

    });
    $("#amount").val("$" + $("#slider-range").slider("values", 0) +
            " - $" + $("#slider-range").slider("values", 1));
});