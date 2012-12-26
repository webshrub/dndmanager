$("#reportDialog").die("pageinit").live("pageinit", function (event, ui) {
    $('#cancelButton').click(function () {
        $('#reportDialog').dialog('close');
    });

    var smsText = window.localStorage.getItem("sendingSmsText");
    $('#smsTextArea').val(smsText);
});

function sendSms() {
    var smsText = window.localStorage.getItem("sendingSmsText");
    new SmsPlugin().send('1909', smsText,
        function () {
            alert('Message sent successfully');
        },
        function (e) {
            alert('Message Failed:' + e);
        }
    );
    $('#reportDialog').dialog('close');
}