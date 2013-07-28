//createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#more").die("pageinit").live("pageinit", function (event, ui) {
    $('#recommendDialogLink').click(function () {
        new RecommendPlugin().share(function () {
                console.log('Share successful');
            },
            function (e) {
                alert('Share failed ' + e);
            }
        );
    });
});