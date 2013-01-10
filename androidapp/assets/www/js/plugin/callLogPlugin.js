var CallLogPlugin = function () {
};

CallLogPlugin.prototype.list = function (params, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'CallLogPlugin', 'list',
        [ params ]);
};
