createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#callLog").die("pageinit").live("pageinit", function (event, ui) {
    document.addEventListener("deviceready", fetchCallLog, false);
});

function fetchCallLog() {
    new CallLogPlugin().list('week',
        function (data) {
            $('#callLogDiv').html(getTemplate("callLog", data));
            $("#listview").listview();
            $("fieldset[name=fs]").controlgroup();
            $("input:checkbox[name=call]").checkboxradio();
        },
        function (e) {
            alert('Fetching of list failed.' + e);
        }
    );

    clearCalls();

    $('input:checkbox[name="call"]').live("change", function () {
        if ($(this).is(':checked')) {
            checkCall();
        } else {
            uncheckCall();
        }
    });

    $('#selectAllCall').click(function () {
        checkUncheckAllCalls();
    });

    $('a[name=reportDialogLink]').live("click", function () {
        var smsText = "COMP TEL NO " + $(this).attr("data-number") + ";" + $(this).attr("data-date") + ";" + "Unknown";
        window.localStorage.setItem("sendingSmsText", smsText);
        $.mobile.changePage('reportDialog.html');
    });
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
