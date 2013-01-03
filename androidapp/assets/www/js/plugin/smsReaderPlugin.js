var SMSReaderPlugin = function () {
};

SMSReaderPlugin.prototype.getInbox = function (params, success, fail) {
    return cordova.exec(function (args) {
        success(args);
    }, function (args) {
        fail(args);
    }, 'SMSReaderPlugin', 'inbox', [params]);
};
SMSReaderPlugin.prototype.delete = function (params, success, fail) {
    return cordova.exec(function (args) {
        success(args);
    }, function (args) {
        fail(args);
    }, 'SMSReaderPlugin', 'delete', [params]);
};

SMSReaderPlugin.prototype.getSent = function (params, success, fail) {
    return cordova.exec(function (args) {
        success(args);
    }, function (args) {
        fail(args);
    }, 'SMSReaderPlugin', 'sent', [params]);
};