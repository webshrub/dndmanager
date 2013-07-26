//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#reportDialog").die("pageshow").live("pageshow", function (event, ui) {
    $('#smsTextArea').val(moonwalkerStorage.getItem("sendingSmsText"));
});

$("#reportDialog").die("pageinit").live("pageinit", function (event, ui) {
    $('#cancelButton').click(function () {
        $('#reportDialog').dialog('close');
    });

    $('#sendSMSButton').click(function () {
        $.mobile.showPageLoadingMsg("b", "Sending message to 1909.", true);
        var smsText = $('#smsTextArea').val();
        var spamNumber = moonwalkerStorage.getItem("spamNumber");

        new SmsPlugin().send(smsText, spamNumber,
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
