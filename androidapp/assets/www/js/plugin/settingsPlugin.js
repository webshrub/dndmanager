var SettingsPlugin = function () {
};

SettingsPlugin.prototype.set = function (key, value, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'SettingsPlugin', 'set_preference', [ key, value ]);
};
