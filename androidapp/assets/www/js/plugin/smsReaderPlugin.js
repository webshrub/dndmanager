var SMSReaderPlugin = function () {
};

SMSReaderPlugin.prototype.getInbox = function (params, success, fail) {
    return cordova.exec(function (args) {
        success(args);
    }, function (args) {
        fail(args);
    }, 'SMSReaderPlugin', 'inbox', [params]);
};