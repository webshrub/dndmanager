//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");
$("#settings").die("pageinit").live("pageinit", function (event, ui) {
    var deleteSMSFlag = moonwalkerStorage.getItem("deleteSMSFlag");
    $("#deleteSmsToggleButton").val(deleteSMSFlag).slider("refresh");

    $("#deleteSmsToggleButton").change(function (event, ui) {
        moonwalkerStorage.setItem("deleteSMSFlag", $(this).val());
    });

    var deleteSentSMSFlag = moonwalkerStorage.getItem("deleteSentSMSFlag");
    $("#deleteSentSMSToggleButton").val(deleteSentSMSFlag).slider("refresh");

    $("#deleteSentSMSToggleButton").change(function (event, ui) {
        moonwalkerStorage.setItem("deleteSentSMSFlag", $(this).val());
    });
});