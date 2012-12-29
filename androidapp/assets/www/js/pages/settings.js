createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#settings").die("pageinit").live("pageinit", function (event, ui) {
    var deleteSMSFlag = moonwalkerStorage.getItem("deleteSMSFlag");
    $("#deleteSmsToggleButton").val(deleteSMSFlag).slider("refresh");
    $("#deleteSmsToggleButton").change(function (event, ui) {
        moonwalkerStorage.setItem("deleteSMSFlag", $(this).val());
    });


    var saveSMSFlag = moonwalkerStorage.getItem("saveMessageFlag");
    $("#saveMessageToggleButton").val(saveSMSFlag).slider("refresh");
    $("#saveMessageToggleButton").change(function (event, ui) {
        moonwalkerStorage.setItem("saveMessageFlag", $(this).val());
    });

    var analyticsFlag = moonwalkerStorage.getItem("analyticsFlag");
    $("#analyticsToggleButton").val(analyticsFlag).slider("refresh");
    $("#analyticsToggleButton").change(function (event, ui) {
        moonwalkerStorage.setItem("analyticsFlag", $(this).val());
    });
});