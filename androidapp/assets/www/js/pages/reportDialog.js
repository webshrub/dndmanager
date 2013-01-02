createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#reportDialog").die("pageinit").live("pageinit", function (event, ui) {
    $('#cancelButton').click(function () {
        $('#reportDialog').dialog('close');
    });

    var smsText = moonwalkerStorage.getItem("sendingSmsText");
    $('#smsTextArea').val(smsText);

    $('#sendSMSButton').click(function () {
        var smsText = $('#smsTextArea').val();
        new SmsPlugin().send('1909', smsText,
            function () {
                var deleteSMSFlag = moonwalkerStorage.getItem("deleteSMSFlag");
                if (deleteSMSFlag == "on") {
                    var startIndex = smsText.indexOf("NO");
                    var endIndex = smsText.indexOf(";");
                    var number = smsText.substring(startIndex + 3, endIndex);
                    new CallLogPlugin().delete(number,
                        function (data) {
                        },
                        function (e) {
//                            alert('Deleting number failed.' + e);
                        }
                    );
                }
            },
            function (e) {
                alert('Message sending Failed:' + e);
            }
        );
        $('#reportDialog').dialog('close');
    });
});