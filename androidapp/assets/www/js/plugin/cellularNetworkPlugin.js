var CellularNetworkPlugin = function () {
};

CellularNetworkPlugin.prototype.getNetworkInfo = function (successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, 'CellularNetworkPlugin', 'getNetworkInfo', []);
};
