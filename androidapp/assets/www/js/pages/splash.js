//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#splash").die("pageshow").live("pageshow", function (event, ui) {
    $.mobile.changePage($('#category'));
});