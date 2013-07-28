var RecommendPlugin = function () {
};

RecommendPlugin.prototype.share = function (successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'RecommendPlugin', "share", [ ]);
};
