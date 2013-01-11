var SMSReaderPlugin = function () {
};

SMSReaderPlugin.prototype.getInbox = function (contactLogFlag, success, fail) {
    return cordova.exec(function (args) {
        success(args);
    }, function (args) {
        fail(args);
    }, 'SMSReaderPlugin', 'inbox', [contactLogFlag]);
};