var IgnoredContactsPlugin = function () {
};

IgnoredContactsPlugin.prototype.list = function (successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'IgnoredContactsPlugin', 'list', [ ]);
};

IgnoredContactsPlugin.prototype.remove = function (ignoredId, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'IgnoredContactsPlugin', 'delete', [ ignoredId ]);
};
