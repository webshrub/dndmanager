createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#callLog").die("pageinit").live("pageinit", function (event, ui) {
    document.addEventListener("deviceready", fetchCallLog, false);
});

function fetchCallLog() {
    new CallLogPlugin().list('3day',
        function (data) {
            $('#callLogDiv').html(getTemplate("callLog", data));
            $("#listview").listview();
            $("fieldset[name=fs]").controlgroup();
            $("input:checkbox[name=call]").checkboxradio();

            clearCalls();

            $('input:checkbox[name=call]').change(function () {
                if ($(this).is(':checked')) {
                    checkCall();
                } else {
                    uncheckCall();
                }
            });

            $('#selectAllCall').click(function () {
                checkUncheckAllCalls();
            });

            $('#reportAllCallButton').click(function () {
                prepareSmsTextObjects();
                $.mobile.changePage('confirmDialog.html');
            });

            $('a[name=reportCallDialogLink]').click(function () {
                var smsText = "COMP TEL NO " + $(this).attr("data-number") + ";" + $(this).attr("data-date") + ";" + "Unknown Company";
                smsText = smsText.substring(0, 160);
                moonwalkerStorage.setItem("sendingSmsText", smsText);
                moonwalkerStorage.setItem("reportType", "call");
                $.mobile.changePage('reportDialog.html');
            });
        },
        function (e) {
            alert('Fetching of list failed.' + e);
        }
    );
}

function checkCall() {
    $('#reportAllCallButton').removeClass('ui-disabled');
    var allChecked = true;
    $('input:checkbox[name="call"]').each(function () {
        if (allChecked) {
            allChecked = $(this).is(':checked');
        }
    });
    if (allChecked) {
        $('#selectAllCall').attr('checked', true);
        $('#selectAllCall').checkboxradio('refresh');
    }
}

function uncheckCall() {
    $('#selectAllCall').attr('checked', false);
    $('#selectAllCall').checkboxradio('refresh');
    var allUnchecked = true;
    $('input:checkbox[name="call"]').each(function () {
        if (allUnchecked) {
            allUnchecked = !$(this).is(':checked');
        }
    });
    if (allUnchecked) {
        $('#reportAllCallButton').addClass('ui-disabled');
    }
}

function checkUncheckAllCalls() {
    $('input:checkbox[name="call"]').attr({checked:$('#selectAllCall').is(':checked')});
    $('input:checkbox[name="call"]').checkboxradio("refresh");
    if ($('#selectAllCall').is(':checked')) {
        $('#reportAllCallButton').removeClass('ui-disabled');
    } else {
        $('#reportAllCallButton').addClass('ui-disabled');
    }
}

function clearCalls() {
    $('#selectAllCall').attr('checked', false);
    $('#selectAllCall').checkboxradio('refresh');

    $('input:checkbox[name="call"]').each(function () {
        $(this).attr('checked', false);
    });
    $('input:checkbox[name="call"]').checkboxradio('refresh');

    $('#reportAllCallButton').addClass('ui-disabled');
}

function prepareSmsTextObjects() {
    var smsTextObjects = [];
    $('input:checkbox[name=call]').filter(':checked').each(function () {
        var reportCallDialogLink = $(this).closest('li').children('a[name=reportCallDialogLink]');
        var number = reportCallDialogLink.attr('data-number');
        var date = reportCallDialogLink.attr('data-date');
        var text = reportCallDialogLink.attr('data-text');
        var smsTextObject = {"number":number, "date":date, "text":text};
        smsTextObjects.push(smsTextObject);
    });
    moonwalkerStorage.setItem("reportType", "call");
    moonwalkerStorage.setItem("smsTextObjects", smsTextObjects);
}