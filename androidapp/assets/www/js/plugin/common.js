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

var moonwalkerStorage = {
    setItem:function (key, value) {
        if (!key || !value) {
            return;
        }
        if (typeof value == "object") {
            value = JSON.stringify(value);
        }
        localStorage.setItem(key, value);
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
{
    if (moonwalkerStorage.getItem("deleteSMSFlag") == null) {
        moonwalkerStorage.setItem("deleteSMSFlag", "off");
    }
    if (moonwalkerStorage.getItem("deleteSentSMSFlag") == null) {
        moonwalkerStorage.setItem("deleteSentSMSFlag", "off");
    }
    if (moonwalkerStorage.getItem("contactLogFlag") == null) {
        moonwalkerStorage.setItem("contactLogFlag", "off");
    }
}