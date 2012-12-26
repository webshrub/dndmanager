createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#reportDialog").die("pageinit").live("pageinit", function (event, ui) {
    $('#cancelButton').click(function () {
        $('#reportDialog').dialog('close');
    });

    var smsText = window.localStorage.getItem("sendingSmsText");
    $('#smsTextArea').val(smsText);

    $('#sendSMSButton').click(function () {
        var smsText = $('#smsTextArea').val();
        new SmsPlugin().send('9810572052', smsText,
            function () {
                alert('Message sent successfully');
            },
            function (e) {
                alert('Message Failed:' + e);
            }
        );
        $('#reportDialog').dialog('close');
    });
});