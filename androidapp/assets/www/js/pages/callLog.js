//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#callLog").die("pageshow").live("pageshow", function (event, ui) {
    clearCalls();
    document.addEventListener("deviceready", fetchCallLog, false);
});

$("#callLog").die("pageinit").live("pageinit", function (event, ui) {
});


function fetchCallLog() {
    var contactLogFlag = moonwalkerStorage.getItem("contactLogFlag");

    new CallLogPlugin().list('3day', contactLogFlag,
        function (data) {
            if (data.rows.length == 0) {
                $('#callLogDiv').html('<p align="center"><strong>Hurray!! You have no spam calls sent in last 3 days.</strong></p>');
            } else {
                $('#callLogDiv').html(getTemplate("callLog", data));
            }
            $('#callLogDiv').trigger('create');

            $('input:checkbox[name=call]').unbind().bind('change', function () {
                if ($(this).is(':checked')) {
                    checkCall();
                } else {
                    uncheckCall();
                }
            });

            $('#selectAllCall').unbind().bind('click', function () {
                checkUncheckAllCalls();
            });

            $('#reportAllCallButton').unbind().bind('click', function () {
                prepareSmsTextObjectsFromCallLog();
                $.mobile.changePage($('#confirmDialog'));
            });

            $('a[name=reportCallDialogLink]').unbind().bind('click', function () {
                var smsText = "Unknown Company" + ", " + $(this).attr("data-number") + ", " + $(this).attr("data-date");
                smsText = smsText.substring(0, 160);
                moonwalkerStorage.setItem("sendingSmsText", smsText);
                moonwalkerStorage.setItem("reportType", "call");
                moonwalkerStorage.setItem("spamNumber", $(this).attr("data-number"));
                $.mobile.changePage($('#reportDialog'));
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

function prepareSmsTextObjectsFromCallLog() {
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