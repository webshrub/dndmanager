createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#smsLog").die("pageinit").live("pageinit", function (event, ui) {
    document.addEventListener("deviceready", fetchSMSLog, false);
});

function fetchSMSLog() {
    new SMSReaderPlugin().getInbox('',
        function (data) {
            $('#smsLogDiv').html(getTemplate("smsLog", data));
            $("#listview").listview();
            $("fieldset[name=fs]").controlgroup();
            $("input:checkbox[name=sms]").checkboxradio();

            clearSMS();

            $('input:checkbox[name="sms"]').change(function () {
                if ($(this).is(':checked')) {
                    checkSMS();
                } else {
                    uncheckSMS();
                }
            });

            $('#selectAllSMS').click(function () {
                checkUncheckAllSMS();
            });

            $('#reportAllSMSButton').click(function () {
                prepareSmsTextObjects();
                $.mobile.changePage('confirmDialog.html');
            });

            $('a[name=reportSMSDialogLink]').click(function () {
                var smsText = "COMP TEL NO " + $(this).attr("data-number") + ";" + $(this).attr("data-date") + ";" + $(this).attr("data-text");
                smsText = smsText.substring(0, 160);
                moonwalkerStorage.setItem("sendingSmsText", smsText);
                moonwalkerStorage.setItem("selectedSmsId", $(this).attr("data-_id"));
                moonwalkerStorage.setItem("reportType", "sms");
                $.mobile.changePage('reportDialog.html');
            });
        },
        function (e) {
            alert('Fetching of list failed.' + e);
        }
    );
}

function checkSMS() {
    $('#reportAllSMSButton').removeClass('ui-disabled');
    var allChecked = true;
    $('input:checkbox[name="sms"]').each(function () {
        if (allChecked) {
            allChecked = $(this).is(':checked');
        }
    });
    if (allChecked) {
        $('#selectAllSMS').attr('checked', true);
        $('#selectAllSMS').checkboxradio('refresh');
    }
}

function uncheckSMS() {
    $('#selectAllSMS').attr('checked', false);
    $('#selectAllSMS').checkboxradio('refresh');
    var allUnchecked = true;
    $('input:checkbox[name="sms"]').each(function () {
        if (allUnchecked) {
            allUnchecked = !$(this).is(':checked');
        }
    });
    if (allUnchecked) {
        $('#reportAllSMSButton').addClass('ui-disabled');
    }
}

function checkUncheckAllSMS() {
    $('input:checkbox[name="sms"]').attr({checked:$('#selectAllSMS').is(':checked')});
    $('input:checkbox[name="sms"]').checkboxradio("refresh");
    if ($('#selectAllSMS').is(':checked')) {
        $('#reportAllSMSButton').removeClass('ui-disabled');
    } else {
        $('#reportAllSMSButton').addClass('ui-disabled');
    }
}

function clearSMS() {
    $('#selectAllSMS').attr('checked', false);
    $('#selectAllSMS').checkboxradio('refresh');

    $('input:checkbox[name="sms"]').each(function () {
        $(this).attr('checked', false);
    });
    $('input:checkbox[name="sms"]').checkboxradio('refresh');

    $('#reportAllSMSButton').addClass('ui-disabled');
}

function prepareSmsTextObjects() {
    var smsTextObjects = [];
    $('input:checkbox[name=sms]').filter(':checked').each(function () {
        var reportSMSDialogLink = $(this).closest('li').children('a[name=reportSMSDialogLink]');
        var number = reportSMSDialogLink.attr('data-number');
        var date = reportSMSDialogLink.attr('data-date');
        var text = reportSMSDialogLink.attr('data-text');
        var smsTextObject = {"number":number, "date":date, "text":text};
        smsTextObjects.push(smsTextObject);
    });
    moonwalkerStorage.setItem("smsTextObjects", smsTextObjects);
}