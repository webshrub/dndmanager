var SmsPlugin = function () {
};

SmsPlugin.prototype.send = function (phone, message, successCallback, failureCallback) {
    alert("Sending " + message + " to " + phone);
    return cordova.exec(successCallback, failureCallback, 'SmsPlugin', "SendSMS", [phone, message]);
};

cordova.addConstructor(function () {
    cordova.addPlugin("SmsPlugin", new SmsPlugin());
    PluginManager.addService("SmsPlugin", "com.webshrub.moonwalker.androidapp.SmsPlugin");
});
