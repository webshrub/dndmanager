//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#confirmDialog").die("pageinit").live("pageinit", function (event, ui) {
    $('#cancelConfirmDialogButton').click(function () {
        $('#confirmDialog').dialog('close');
    });

    $('#sendConfirmDialogButton').click(function () {
        $.mobile.showPageLoadingMsg("b", "Sending Messages to 1909", true);
        var smsTextObjects = moonwalkerStorage.getItem("smsTextObjects");

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
            var smsTextObject = smsTextObjects[counter];
            var smsText = prepareSmsTextFromObject(smsTextObject);
            new SmsPlugin().send(smsText, smsTextObject.number,
                closeDialog, function (e) {
                    alert('Message Failed:' + e);
                }
            );
        }
    });
});