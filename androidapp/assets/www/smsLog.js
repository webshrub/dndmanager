createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");
$("#smsLog").die("pageinit").live("pageinit", function (event, ui) {
    clearSMS();

    $('input[name="sms"]').click(function () {
        if ($(this).is(':checked')) {
            checkSMS();
        } else {
            uncheckSMS();
        }
    });

    $('#selectAllSMS').click(function () {
        checkUncheckAllSMS();
    });
});

function checkSMS() {
    $('#j_66').removeClass('ui-disabled');
    var allChecked = true;
    $('input[name="sms"]').each(function () {
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
    $('input[name="sms"]').each(function () {
        if (allUnchecked) {
            allUnchecked = !$(this).is(':checked');
        }
    });
    if (allUnchecked) {
        $('#j_66').addClass('ui-disabled');
    }
}

function checkUncheckAllSMS() {
    $('input[name="sms"]').attr({checked:$('#selectAllSMS').is(':checked')});
    $('input[name="sms"]').checkboxradio("refresh");
    if ($('#selectAllSMS').is(':checked')) {
        $('#j_66').removeClass('ui-disabled');
    } else {
        $('#j_66').addClass('ui-disabled');
    }
}

function clearSMS() {
    $('#selectAllSMS').attr('checked', false);
    $('#selectAllSMS').checkboxradio('refresh');

    $('input[name="sms"]').each(function () {
        $(this).attr('checked', false);
    });
    $('input[name="sms"]').checkboxradio('refresh');

    $('#j_66').addClass('ui-disabled');
}
