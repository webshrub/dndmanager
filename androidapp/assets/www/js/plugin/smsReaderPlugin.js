var SMSReaderPlugin = function () {
};

SMSReaderPlugin.prototype.getInbox = function (params, success, fail) {
    return cordova.exec(function (args) {
        success(args);
    }, function (args) {
        fail(args);
    }, 'SMSReaderPlugin', 'inbox', [params]);
};

SMSReaderPlugin.prototype.getSent = function (params, success, fail) {
    return cordova.exec(function (args) {
        success(args);
    }, function (args) {
        fail(args);
    }, 'SMSReaderPlugin', 'sent', [params]);
};

cordova.addConstructor(function () {
    alert("Inside add Constructor");
    cordova.addPlugin("SMSReaderPlugin", new SMSReaderPlugin());
    PluginManager.addService("SMSReaderPlugin", "com.webshrub.moonwalker.androidapp.SMSReaderPlugin");
});