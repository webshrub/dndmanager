//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#category").die("pageshow").live("pageshow", function (event, ui) {
    clearCategories();
});

$("#category").die("pageinit").live("pageinit", function (event, ui) {
    document.addEventListener("deviceready", fetchNetworkInfo, false);

    $('#unblockButton').click(function () {
        blockUnblock(false);
        $.mobile.changePage($("#reportDialog"));
    });

    $('#blockButton').click(function () {
        blockUnblock(true);
        $.mobile.changePage($("#reportDialog"));
    });

    $('input:checkbox[name="category"]').click(function () {
        if ($(this).is(':checked')) {
            checkCategory();
        } else {
            uncheckCategory();
        }
    });

    $('#selectAllCategory').click(function () {
        checkUncheckAllCategories();
    });
});

function checkCategory() {
    $('#unblockButton').removeClass('ui-disabled');
    $('#blockButton').removeClass('ui-disabled');
    var allChecked = true;
    $('input:checkbox[name="category"]').each(function () {
        if (allChecked) {
            allChecked = $(this).is(':checked');
        }
    });
    if (allChecked) {
        $('#selectAllCategory').attr('checked', true);
        $('#selectAllCategory').checkboxradio('refresh');
    }
}

function uncheckCategory() {
    $('#selectAllCategory').attr('checked', false);
    $('#selectAllCategory').checkboxradio('refresh');
    var allUnchecked = true;
    $('input:checkbox[name="category"]').each(function () {
        if (allUnchecked) {
            allUnchecked = !$(this).is(':checked');
        }
    });
    if (allUnchecked) {
        $('#unblockButton').addClass('ui-disabled');
        $('#blockButton').addClass('ui-disabled');
    }
}

function checkUncheckAllCategories() {
    $('input:checkbox[name="category"]').attr({checked:$('#selectAllCategory').is(':checked')});
    $('input:checkbox[name="category"]').checkboxradio("refresh");
    if ($('#selectAllCategory').is(':checked')) {
        $('#unblockButton').removeClass('ui-disabled');
        $('#blockButton').removeClass('ui-disabled');
    } else {
        $('#unblockButton').addClass('ui-disabled');
        $('#blockButton').addClass('ui-disabled');
    }
}

function clearCategories() {
    $('#selectAllCategory').attr('checked', false);
    $('#selectAllCategory').checkboxradio('refresh');

    $('input:checkbox[name="category"]').each(function () {
        $(this).attr('checked', false);
    });
    $('input:checkbox[name="category"]').checkboxradio('refresh');

    $('#unblockButton').addClass('ui-disabled');
    $('#blockButton').addClass('ui-disabled');
}

function blockUnblock(isBlock) {
    var smsText = isBlock ? "STOP " : "START ";
    var values = [];
    var isAllChecked = false;
    $("input:checkbox").each(function () {
        if ($(this).is(':checked')) {
            values.push($(this).val());
            if ($(this).val() == "0") {
                isAllChecked = true;
            }
        }
    });
    if (isAllChecked) {
        smsText = isBlock ? "START " : "STOP ";
    }
    smsText = isAllChecked ? smsText + "0" : smsText + values.toString();
    moonwalkerStorage.setItem("sendingSmsText", smsText);
}

function fetchNetworkInfo() {
    new CellularNetworkPlugin().getNetworkInfo(function (data) {
            var networkInfo = moonwalkerStorage.getItem("networkInfo");
            var networkInfoKey = data.mcc + "-" + data.mnc;
            var networkInfoValue = networkInfo[networkInfoKey];
            var networkInfoMessage = "Operator: " + networkInfoValue['operator'] + ", Circle: " + networkInfoValue['circle'];
            $.mobile.showPageLoadingMsg("b", networkInfoMessage, true);
            setTimeout(function () {
                $.mobile.hidePageLoadingMsg();
            }, 2000);
        },
        function (e) {
            alert('Unable to fetch network information:' + e);
        }
    );
}
