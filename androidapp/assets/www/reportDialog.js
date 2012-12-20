$("#j_56").die("pageinit").live("pageinit", function (event, ui) {
    var smsText = window.localStorage.getItem("sendingSmsText");
    $('#j_61').val(smsText);

});

$('#j_68').click(function () {
    $('#j_56').dialog('close');
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
    $('#j_56').dialog('close');
}