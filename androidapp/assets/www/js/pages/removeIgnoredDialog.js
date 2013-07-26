//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#removeIgnoredDialog").die("pageinit").live("pageinit", function (event, ui) {
    $('#cancelRemoveIgnoredDialogButton').click(function () {
        $('#removeIgnoredDialog').dialog('close');
    });

    $('#confirmRemoveIgnoredDialogButton').click(function () {
        $.mobile.showPageLoadingMsg("b", "Removing numbers from ignored list", true);
        var removeIgnoredIds = moonwalkerStorage.getItem("removeIgnoredIds");
        var totalCounter = removeIgnoredIds.length;
        var closeDialog = function () {
            totalCounter -= 1;
            if (totalCounter <= 0) {
                $.mobile.hidePageLoadingMsg();
                $.mobile.showPageLoadingMsg("b", "All numbers removed from ignored list.", true);
                $('#removeIgnoredDialog').dialog('close');
            }
        };
        for (var counter = 0; counter < removeIgnoredIds.length; counter++) {
            new IgnoredContactsPlugin().remove(removeIgnoredIds[counter], closeDialog, function (e) {
                    alert('Remove Ignored Contacts Failed:' + e);
                }
            );
        }
    });
});