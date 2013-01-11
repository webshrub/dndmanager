var CallLogPlugin = function () {
};

CallLogPlugin.prototype.list = function (numberOfDays, contactLogFlag, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'CallLogPlugin', 'list',
        [ numberOfDays, contactLogFlag ]);
};
