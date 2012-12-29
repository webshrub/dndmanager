createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#settings").die("pageinit").live("pageinit", function (event, ui) {
    var value = moonwalkerStorage.getItem("deleteSMSFlag");
    $("#j_47").val(value).slider("refresh");
    $("#j_47").change(function (event, ui) {
        moonwalkerStorage.setItem("deleteSMSFlag", $(this).val());
    });


    var value1 = moonwalkerStorage.getItem("saveMessageFlag");
    $("#j_50").val(value1).slider("refresh");
    $("#j_50").change(function (event, ui) {
        moonwalkerStorage.setItem("saveMessageFlag", $(this).val());
    });

    var value2 = moonwalkerStorage.getItem("helpFlag");
    $("#j_54").val(value2).slider("refresh");
    $("#j_54").change(function (event, ui) {
        moonwalkerStorage.setItem("helpFlag", $(this).val());
    });
});