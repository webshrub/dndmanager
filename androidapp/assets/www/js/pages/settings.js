//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#settings").die("pageshow").live("pageshow", function (event, ui) {
    var deleteSMSFlag = moonwalkerStorage.getItem("deleteSMSFlag");
    $("#deleteSmsToggleButton").val(deleteSMSFlag).slider("refresh");

    var deleteSentSMSFlag = moonwalkerStorage.getItem("deleteSentSMSFlag");
    $("#deleteSentSMSToggleButton").val(deleteSentSMSFlag).slider("refresh");
});

$("#settings").die("pageinit").live("pageinit", function (event, ui) {
    var deleteSMSFlag = moonwalkerStorage.getItem("deleteSMSFlag");
    if (deleteSMSFlag == null) {
        deleteSMSFlag = "off";
        moonwalkerStorage.setItem("deleteSMSFlag", deleteSMSFlag);
    }
    $("#deleteSmsToggleButton").val(deleteSMSFlag).slider("refresh");
    $("#deleteSmsToggleButton").change(function (event, ui) {
        moonwalkerStorage.setItem("deleteSMSFlag", $(this).val());
    });

    var deleteSentSMSFlag = moonwalkerStorage.getItem("deleteSentSMSFlag");
    if (deleteSentSMSFlag == null) {
        deleteSentSMSFlag = "off";
        moonwalkerStorage.setItem("deleteSentSMSFlag", deleteSentSMSFlag);
    }
    $("#deleteSentSMSToggleButton").val(deleteSentSMSFlag).slider("refresh");
    $("#deleteSentSMSToggleButton").change(function (event, ui) {
        moonwalkerStorage.setItem("deleteSentSMSFlag", $(this).val());
    });
});