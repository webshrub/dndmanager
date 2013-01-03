var SmsPlugin = function () {
};

SmsPlugin.prototype.send = function (phone, message, reportType, isToDelete, number, successCallback, failureCallback) {
//    alert("Sending " + message + " to " + phone);
    return cordova.exec(successCallback, failureCallback, 'SmsPlugin', "SendSMS", ["phone", message, reportType, isToDelete, number]);
};
