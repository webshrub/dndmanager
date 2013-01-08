//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#reportDialog").die("pageshow").live("pageshow", function (event, ui) {
    var smsText = moonwalkerStorage.getItem("sendingSmsText");
    $('#smsTextArea').val(smsText);
});

$("#reportDialog").die("pageinit").live("pageinit", function (event, ui) {
    $('#cancelButton').click(function () {
        $('#reportDialog').dialog('close');
    });

    $('#sendSMSButton').click(function () {
        $.mobile.showPageLoadingMsg("b", "Sending message to 1909.", true);
        var smsText = $('#smsTextArea').val();
        var deleteSMSFlag = moonwalkerStorage.getItem("deleteSMSFlag");
        var reportType = moonwalkerStorage.getItem("reportType");
        var deleteSentSMSFlag = moonwalkerStorage.getItem("deleteSentSMSFlag");
        var spamNumber = moonwalkerStorage.getItem("spamNumber");

        new SmsPlugin().send('1909', smsText, reportType, deleteSMSFlag, deleteSentSMSFlag, spamNumber,
            function () {
                $.mobile.hidePageLoadingMsg();
                $.mobile.showPageLoadingMsg("b", "Message sent.", true);
                $('#reportDialog').dialog('close');
            },
            function (e) {
                alert('Message sending Failed:' + e);
            }
        );
    });
});