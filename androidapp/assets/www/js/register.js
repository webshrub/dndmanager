$(document).ready(function () {
    $("#select_all_checkbox").change(function () {
        ($(this).is(':checked')) ? $('.checkbox').prop('checked', true) : $('.checkbox').prop('checked', false);
    });

    $(".checkbox").change(function () {
        var isAllChecked = true;
        $(".checkbox").each(function () {
            if (!$(this).is(':checked')) {
                isAllChecked = false;
            }
        });
        isAllChecked ? $('#select_all_checkbox').prop('checked', true) : $('#select_all_checkbox').prop('checked', false);
    });
});

function register() {
    var smsText = "START ";
    var isFirst = true;
    $(':checkbox').each(function () {
        if ($(this).is(':checked')) {
            if (isFirst) {
                smsText = smsText + $(this).val();
                isFirst = false;
            } else {
                smsText = smsText + "," + $(this).val();
            }
        }
    });
    new SmsPlugin().send('1909', smsText,
        function () {
            alert('Message sent successfully');
        },
        function (e) {
            alert('Message Failed:' + e);
        }
    );
}

function unregister() {
    var smsText = "STOP ";
    var isFirst = 'true';
    $(':checkbox').each(function () {
        if ($(this).is(':checked')) {
            if (isFirst) {
                smsText = smsText + $(this).val();
                isFirst = false;
            } else {
                smsText = smsText + "," + $(this).val();
            }
        }
    });
    new SmsPlugin().send('1909', smsText,
        function () {
            alert('Message sent successfully');
        },
        function (e) {
            alert('Message Failed:' + e);
        }
    );
}