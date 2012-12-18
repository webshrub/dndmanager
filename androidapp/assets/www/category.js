createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");
$("#category").die("pageinit").live("pageinit", function (event, ui) {
    clearCategories();

    $('input[name="category"]').click(function () {
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
    $('#j_81').removeClass('ui-disabled');
    $('#j_83').removeClass('ui-disabled');
    var allChecked = true;
    $('input[name="category"]').each(function () {
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
    $('input[name="category"]').each(function () {
        if (allUnchecked) {
            allUnchecked = !$(this).is(':checked');
        }
    });
    if (allUnchecked) {
        $('#j_81').addClass('ui-disabled');
        $('#j_83').addClass('ui-disabled');
    }
}

function checkUncheckAllCategories() {
    $('input[name="category"]').attr({checked:$('#selectAllCategory').is(':checked')});
    $('input[name="category"]').checkboxradio("refresh");
    if ($('#selectAllCategory').is(':checked')) {
        $('#j_81').removeClass('ui-disabled');
        $('#j_83').removeClass('ui-disabled');
    } else {
        $('#j_81').addClass('ui-disabled');
        $('#j_83').addClass('ui-disabled');
    }
}

function clearCategories() {
    $('#selectAllCategory').attr('checked', false);
    $('#selectAllCategory').checkboxradio('refresh');

    $('input[name="category"]').each(function () {
        $(this).attr('checked', false);
    });
    $('input[name="category"]').checkboxradio('refresh');

    $('#j_81').addClass('ui-disabled');
    $('#j_83').addClass('ui-disabled');
}
