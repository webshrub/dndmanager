/* Get the template from remote url, Also caches the template for further reuse. */
function getTemplate(templateName, json) {
    if (!ich.templates[templateName]) {
        $.ajax({
            url:"templates/" + templateName + ".tpl",
            dataType:"html",
            async:false,
            success:function (template) {
                ich.addTemplate(templateName, template);
            }
        });
    }
    return ich[templateName](json, true);
}

function prepareSmsTextObjectsFromLog(reportDialogLinks, reportType) {
    var smsTextObjects = [];
    $(reportDialogLinks).each(function () {
        var reportDialogLink = $(this);
        var number = reportDialogLink.attr('data-number');
        var date = reportDialogLink.attr('data-date');
        var datetime = reportDialogLink.attr('data-datetime');
        var text = reportDialogLink.attr('data-text');
        var smsTextObject = {"number":number, "date":date, "datetime":datetime, "text":text};
        smsTextObjects.push(smsTextObject);
    });
    moonwalkerStorage.setItem("reportType", reportType);
    moonwalkerStorage.setItem("smsTextObjects", smsTextObjects);
}

function prepareSmsText(reportDialogLink, reportType) {
    var number = reportDialogLink.attr('data-number');
    var date = reportDialogLink.attr('data-date');
    var datetime = reportDialogLink.attr('data-datetime');
    var text = reportDialogLink.attr('data-text');
    var smsText = prepareSmsTextFromFormat(number, date, datetime, text);
    moonwalkerStorage.setItem('sendingSmsText', smsText);
    moonwalkerStorage.setItem('spamNumber', number);
    moonwalkerStorage.setItem("reportType", reportType);
}

function prepareSmsTextFromObject(smsTextObject) {
    return prepareSmsTextFromFormat(smsTextObject.number, smsTextObject.date, smsTextObject.datetime, smsTextObject.text);
}

function prepareSmsTextFromFormat(number, date, datetime, text) {
    try {
        text = text.replace(/,/g, " ");
        var format = moonwalkerStorage.getItem('smsFormat');
        var smsText = format.replace('~~number~~', number);
        smsText = smsText.replace('~~date~~', date);
        smsText = smsText.replace('~~datetime~~', datetime);
        smsText = smsText.replace('~~text~~', text);
        return smsText;
    } catch (e) {
        return 'COMP TEL NO ' + number + ';' + datetime + ';' + text;
    }
}

var moonwalkerStorage = {
    setItem:function (key, value) {
        if (!key || !value) {
            return;
        }
        if (typeof value == "object") {
            value = JSON.stringify(value);
        }
        localStorage.setItem(key, value);
        new SettingsPlugin().set(key,value, function(){},function(){});
    },
    getItem:function (key) {
        var value = localStorage.getItem(key);
        if (!value) {
            return null;
        }
        // assume it is an object that has been stringified
        if (value[0] == "{" || value[0] == "[") {
            value = JSON.parse(value);
        }
        return value;
    }
};
if (moonwalkerStorage.getItem("deleteSMSFlag") == null) {
    moonwalkerStorage.setItem("deleteSMSFlag", "on");
}
if (moonwalkerStorage.getItem("deleteSentSMSFlag") == null) {
    moonwalkerStorage.setItem("deleteSentSMSFlag", "off");
}
if (moonwalkerStorage.getItem("contactLogFlag") == null) {
    moonwalkerStorage.setItem("contactLogFlag", "off");
}

if (moonwalkerStorage.getItem("networkInfo") == null) {
    var networkInfo = new Object();
    $.ajax({
        type:"GET",
        url:"data.csv",
        dataType:"text",
        success:function (allText) {
            var allTextLines = allText.split(/\r\n|\n/);
            for (var i = 0; i < allTextLines.length; i++) {
                var data = allTextLines[i].split('###');
                var mcc = parseInt(data[0].split('-')[0]);
                var mnc = parseInt(data[0].split('-')[1]);
                var operator = data[1];
                var circle = data[2];
                var format = data[3];
                var mccmnc = mcc.toString() + '-' + mnc.toString();
                networkInfo[mccmnc] = {'operator':operator, 'circle':circle, 'format':format};
            }
            moonwalkerStorage.setItem("networkInfo", networkInfo);
        }
    });
}