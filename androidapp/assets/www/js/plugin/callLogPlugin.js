var CallLogPlugin = function () {
};

CallLogPlugin.prototype.list = function (params, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'CallLogPlugin', 'list',
        [ params ]);
};

CallLogPlugin.prototype.contact = function (params, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'CallLogPlugin', 'contact',
        [ params ]);
};

CallLogPlugin.prototype.show = function (params, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'CallLogPlugin', 'show',
        [ params ]);
};

cordova.addConstructor(function () {
    alert("Inside constructor.");
    cordova.addPlugin('CallLogPlugin', new CallLogPlugin());
    PluginManager.addService("CallLogPlugin", "com.webshrub.moonwalker.androidapp.CallLogPlugin");
});