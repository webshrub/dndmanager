var SMSReaderPlugin = function () {
};

SMSReaderPlugin.prototype.getInbox = function (success, fail) {
    return cordova.exec(function (args) {
        success(args);
    }, function (args) {
        fail(args);
    }, 'SMSReaderPlugin', 'inbox', [ ]);
};