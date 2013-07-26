var SmsPlugin = function () {
};

SmsPlugin.prototype.send = function (message, spamNumber, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'SmsPlugin', "SendSMS", [message, spamNumber]);
};
