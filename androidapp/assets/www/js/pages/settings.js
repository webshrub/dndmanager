//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");
$("#settings").die("pageinit").live("pageinit", function (event, ui) {
    var smsFormat = moonwalkerStorage.getItem("smsFormat");
    $('#smsFormat option[data-format="' + smsFormat + '"]').attr('selected', 'selected');
    $('#smsFormat').selectmenu('refresh');

    $("#smsFormat").change(function (event, ui) {
        var smsFormat = $(this).find('option:selected').attr('data-format');
        moonwalkerStorage.setItem("smsFormat", smsFormat);
    });

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

    var contactLogFlag = moonwalkerStorage.getItem("contactLogFlag");
    $("#contactLogToggleButton").val(contactLogFlag).slider("refresh");

    $("#contactLogToggleButton").change(function (event, ui) {
        moonwalkerStorage.setItem("contactLogFlag", $(this).val());
    });

    var showNotificationFlag = moonwalkerStorage.getItem("showNotificationFlag");
    $("#showNotificationToggleButton").val(showNotificationFlag).slider("refresh");

    $("#showNotificationToggleButton").change(function (event, ui) {
        moonwalkerStorage.setItem("showNotificationFlag", $(this).val());
    });
});