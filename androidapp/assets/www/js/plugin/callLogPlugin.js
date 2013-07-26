var CallLogPlugin = function () {
};

CallLogPlugin.prototype.list = function (successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'CallLogPlugin', 'list', [ ]);
};
