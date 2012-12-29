createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");
$("#splash").die("pageinit").live("pageinit", function (event, ui) {
    $.mobile.changePage('callLog.html');
});