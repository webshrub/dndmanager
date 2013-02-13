//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#smsLog").die("pageshow").live("pageshow", function (event, ui) {
    clearSMS();
    document.addEventListener("deviceready", fetchSMSLog, false);
});

$("#smsLog").die("pageinit").live("pageinit", function (event, ui) {
});

function fetchSMSLog() {
    var contactLogFlag = moonwalkerStorage.getItem("contactLogFlag");

    new SMSReaderPlugin().getInbox(contactLogFlag,
        function (data) {
            if (data.messages.length == 0) {
                $('#smsLogDiv').html('<p align="center"><strong>Hurray!! You have no spam SMS sent in last 3 days.</strong></p>');
            } else {
                $('#smsLogDiv').html(getTemplate("smsLog", data));
            }
            $('#smsLogDiv').trigger('create');

            $('input:checkbox[name="sms"]').unbind().bind('change', function () {
                if ($(this).is(':checked')) {
                    checkSMS();
                } else {
                    uncheckSMS();
                }
            });

            $('#selectAllSMS').unbind().bind('click', function () {
                checkUncheckAllSMS();
            });

            $('#reportAllSMSButton').unbind().bind('click', function () {
                var reportSMSDialogLinks = [];
                $('input:checkbox[name=sms]').filter(':checked').each(function () {
                    var reportSMSDialogLink = $(this).closest('li').children('a[name=reportSMSDialogLink]');
                    reportSMSDialogLinks.push(reportSMSDialogLink);
                });
                prepareSmsTextObjectsFromLog(reportSMSDialogLinks, 'sms');
                $.mobile.changePage($('#confirmDialog'));
            });

            $('a[name=reportSMSDialogLink]').unbind().bind('click', function () {
                prepareSmsText($(this), 'sms');
                $.mobile.changePage($('#reportDialog'));
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