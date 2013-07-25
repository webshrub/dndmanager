//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#ignored").die("pageshow").live("pageshow", function (event, ui) {
    clearIgnored();
    document.addEventListener("deviceready", fetchIgnored, false);
});

$("#ignored").die("pageinit").live("pageinit", function (event, ui) {
});


function fetchIgnored() {
    $.mobile.showPageLoadingMsg("b", "Loading Ignored Contacts...", true);

    new IgnoredContactsPlugin().list(
        function (data) {
            if (data.rows.length == 0) {
                $('#ignoredDiv').html('<p align="center"><strong>No Ignored Contacts found.</strong></p>');
            } else {
                $('#ignoredDiv').html(getTemplate("ignored", data));
            }
            $('#ignoredDiv').trigger('create');

            $('input:checkbox[name=ignored]').unbind().bind('change', function () {
                if ($(this).is(':checked')) {
                    checkIgnored();
                } else {
                    uncheckIgnored();
                }
            });

            $('#selectAllIgnored').unbind().bind('click', function () {
                checkUncheckAllIgnored();
            });

            $('#removeAllIgnoredButton').unbind().bind('click', function () {
                var removeIgnoredIds = [];
                $('input:checkbox[name=ignored]').filter(':checked').each(function () {
                    var removeIgnoredDialogLink = $(this).closest('li').children('a[name=removeIgnoredDialogLink]');
                    removeIgnoredIds.push(removeIgnoredDialogLink.attr('data-id'));
                });
                moonwalkerStorage.setItem('removeIgnoredIds', removeIgnoredIds);
                $.mobile.changePage($('#removeIgnoredDialog'));
            });

            $('a[name=removeIgnoredDialogLink]').unbind().bind('click', function () {
                var removeIgnoredIds = [];
                removeIgnoredIds.push($(this).attr('data-id'));
                moonwalkerStorage.setItem('removeIgnoredIds', removeIgnoredIds);
                $.mobile.changePage($('#removeIgnoredDialog'));
            });
            $.mobile.hidePageLoadingMsg();
        },
        function (e) {
            alert('Fetching of list failed.' + e);
        }
    );
}

function checkIgnored() {
    $('#removeAllIgnoredButton').removeClass('ui-disabled');
    var allChecked = true;
    $('input:checkbox[name="ignored"]').each(function () {
        if (allChecked) {
            allChecked = $(this).is(':checked');
        }
    });
    if (allChecked) {
        $('#selectAllIgnored').attr('checked', true);
        $('#selectAllIgnored').checkboxradio('refresh');
    }
}

function uncheckIgnored() {
    $('#selectAllIgnored').attr('checked', false);
    $('#selectAllIgnored').checkboxradio('refresh');
    var allUnchecked = true;
    $('input:checkbox[name="ignored"]').each(function () {
        if (allUnchecked) {
            allUnchecked = !$(this).is(':checked');
        }
    });
    if (allUnchecked) {
        $('#removeAllIgnoredButton').addClass('ui-disabled');
    }
}

function checkUncheckAllIgnored() {
    $('input:checkbox[name="ignored"]').attr({checked:$('#selectAllIgnored').is(':checked')});
    $('input:checkbox[name="ignored"]').checkboxradio("refresh");
    if ($('#selectAllIgnored').is(':checked')) {
        $('#removeAllIgnoredButton').removeClass('ui-disabled');
    } else {
        $('#removeAllIgnoredButton').addClass('ui-disabled');
    }
}

function clearIgnored() {
    $('#selectAllIgnored').attr('checked', false);
    $('#selectAllIgnored').checkboxradio('refresh');

    $('input:checkbox[name="ignored"]').each(function () {
        $(this).attr('checked', false);
    });
    $('input:checkbox[name="ignored"]').checkboxradio('refresh');

    $('#removeAllIgnoredButton').addClass('ui-disabled');
}