/**
 * Created by IntelliJ IDEA.
 * Author: kaarel Kargu(karq)
 * Date: 8/24/11
 * PhoneGAP sms inbox/sent reading plugin(js part)
 */

var SMSReader = function () {
};

SMSReader.prototype.getInbox = function (params, success, fail) {
    alert("Inside smsread.js get Inbox.");
    return cordova.exec(function (args) {
        success(args);
    }, function (args) {
        fail(args);
    }, 'SMSReader', 'inbox', [params]);
};

SMSReader.prototype.getSent = function (params, success, fail) {
    return cordova.exec(function (args) {
        success(args);
    }, function (args) {
        fail(args);
    }, 'SMSReader', 'sent', [params]);
};

PhoneGap.addConstructor(function () {
    alert("Inside add Constructor.");
    cordova.addPlugin("SMSReader", new SMSReader());
    PluginManager.addService("SMSReader", "com.webshrub.moonwalker.androidapp.SMSReader");
});