var CallLog = function () {
};

CallLog.prototype.list = function (params, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'CallLogPlugin', 'list',
        [ params ]);
};

CallLog.prototype.contact = function (params, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'CallLogPlugin', 'contact',
        [ params ]);
};

CallLog.prototype.show = function (params, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'CallLogPlugin', 'show',
        [ params ]);
};

cordova.addConstructor(function () {
    cordova.addPlugin('CallLogPlugin', new CallLog());
    PluginManager.addService("CallLogPlugin", "com.webshrub.moonwalker.androidapp.CallLogPlugin");
});