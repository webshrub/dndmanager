createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#reportDialog").die("pageinit").live("pageinit", function (event, ui) {
    $('#cancelButton').click(function () {
        $('#reportDialog').dialog('close');
    });

    var smsText = moonwalkerStorage.getItem("sendingSmsText");
    $('#smsTextArea').val(smsText);

    $('#sendSMSButton').click(function () {
        var smsText = $('#smsTextArea').val();
        var deleteSMSFlag = moonwalkerStorage.getItem("deleteSMSFlag");
        var reportType = moonwalkerStorage.getItem("reportType");
        var saveSMSFlag = moonwalkerStorage.getItem("saveMessageFlag");
        var spamNumber = moonwalkerStorage.getItem("spamNumber");

        new SmsPlugin().send('1909', smsText, reportType, deleteSMSFlag, saveSMSFlag, spamNumber,
            function () {
            },
            function (e) {
                alert('Message sending Failed:' + e);
            }
        );
        $('#reportDialog').dialog('close');
    });
});