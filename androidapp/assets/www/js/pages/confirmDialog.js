//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#confirmDialog").die("pageinit").live("pageinit", function (event, ui) {
    $('#cancelConfirmDialogButton').click(function () {
        $('#confirmDialog').dialog('close');
    });

    $('#sendConfirmDialogButton').click(function () {
        $.mobile.showPageLoadingMsg("b", "Sending Messages to 1909", true);
        var deleteSMSFlag = moonwalkerStorage.getItem("deleteSMSFlag");
        var reportType = moonwalkerStorage.getItem("reportType");
        var smsTextObjects = moonwalkerStorage.getItem("smsTextObjects");
        var saveSMSFlag = moonwalkerStorage.getItem("saveMessageFlag");

        var totalCounter = smsTextObjects.length;
        var closeDialog = function () {
            totalCounter -= 1;
            if (totalCounter <= 0) {
                $.mobile.hidePageLoadingMsg();
                $.mobile.showPageLoadingMsg("b", "Messages sent.", true);
                $('#confirmDialog').dialog('close');
            }
        };
        for (var counter = 0; counter < smsTextObjects.length; counter++) {
            var smsText = "COMP TEL NO " + smsTextObjects[counter].number + ";" + smsTextObjects[counter].date + ";" + smsTextObjects[counter].text;
            new SmsPlugin().send('1909', smsText, reportType, deleteSMSFlag, saveSMSFlag, smsTextObjects[counter].number,
                closeDialog, function (e) {
                    alert('Message Failed:' + e);
                }
            );
        }
    });
});