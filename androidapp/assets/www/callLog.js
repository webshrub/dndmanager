createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#callLog").die("pageinit").live("pageinit", function (event, ui) {
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
}

function checkCall() {
    $('#reportAllButton').removeClass('ui-disabled');
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
        $('#reportAllButton').addClass('ui-disabled');
    }
}

function checkUncheckAllCalls() {
    $('input:checkbox[name="call"]').attr({checked:$('#selectAllCall').is(':checked')});
    $('input:checkbox[name="call"]').checkboxradio("refresh");
    if ($('#selectAllCall').is(':checked')) {
        $('#reportAllButton').removeClass('ui-disabled');
    } else {
        $('#reportAllButton').addClass('ui-disabled');
    }
}

function clearCalls() {
    $('#selectAllCall').attr('checked', false);
    $('#selectAllCall').checkboxradio('refresh');

    $('input:checkbox[name="call"]').each(function () {
        $(this).attr('checked', false);
    });
    $('input:checkbox[name="call"]').checkboxradio('refresh');

    $('#reportAllButton').addClass('ui-disabled');
}


/* Get the template from remote url, Also caches the template for further reuse. */
function getTemplate(templateName, json) {
    if (!ich.templates[templateName]) {
        $.ajax({
            url:"templates/" + templateName + ".tpl",
            dataType:"html",
            async:false,
            success:function (template) {
                ich.addTemplate(templateName, template);
            }
        });
    }
    return ich[templateName](json, true);
}

