createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#confirmDialog").die("pageinit").live("pageinit", function (event, ui) {
    $('#cancelConfirmDialogButton').click(function () {
        $('#confirmDialog').dialog('close');
    });

    $('#sendConfirmDialogButton').click(function () {
        var smsTextObjects = moonwalkerStorage.getItem("smsTextObjects");
        for (var counter = 0; counter < smsTextObjects.length; counter++) {
            var smsText = "COMP TEL NO " + smsTextObjects[counter].number + ";" + smsTextObjects[counter].date + ";" + smsTextObjects[counter].text;
            new SmsPlugin().send('1909', smsText,
                function () {
                    alert('Message sent successfully');
                },
                function (e) {
                    alert('Message Failed:' + e);
                }
            );
        }
        $('#confirmDialog').dialog('close');
    });
});