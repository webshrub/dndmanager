/*
 * JS for confirmDialog generated by Exadel Tiggzi
 *
 * Created on: Monday, November 26, 2012, 12:54:08 AM (PST)
 */
/************************************
 * JS API provided by Exadel Tiggzi  *
 ************************************/
/* Setting project environment indicator */
Tiggr.env = "apk";
Tiggr.getProjectGUID = function () {
    return '314e04cd-42b8-462c-a1df-39dbb95353ec';
}
Tiggr.getTargetPlatform = function () {
    return 'A';
}

function navigateTo(outcome, useAjax) {
    Tiggr.navigateTo(outcome, useAjax);
}

function adjustContentHeight() {
    Tiggr.adjustContentHeight();
}

function adjustContentHeightWithPadding() {
    Tiggr.adjustContentHeightWithPadding();
}

function setDetailContent(pageUrl) {
    Tiggr.setDetailContent(pageUrl);
}
/**********************
 * SECURITY CONTEXTS  *
 **********************/
/*******************************
 *      SERVICE SETTINGS        *
 ********************************/
/*************************
 *      SERVICES          *
 *************************/
createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");
Tiggr.AppPages = [
    {
        "name":"faq",
        "location":"faq.html"
    },
    {
        "name":"alertDialog",
        "location":"alertDialog.html"
    },
    {
        "name":"splash",
        "location":"splash.html"
    },
    {
        "name":"callLog",
        "location":"callLog.html"
    },
    {
        "name":"more",
        "location":"more.html"
    },
    {
        "name":"about",
        "location":"about.html"
    },
    {
        "name":"settings",
        "location":"settings.html"
    },
    {
        "name":"smsLog",
        "location":"smsLog.html"
    },
    {
        "name":"category",
        "location":"category.html"
    },
    {
        "name":"confirmDialog",
        "location":"confirmDialog.html"
    }
];
j_95_js = function (runBeforeShow) { /* Object & array with components "name-to-id" mapping */
    var n2id_buf = {
        'mobilegrid':'j_96',
        'mobilegridcell_c968':'j_97',
        'mobilelabel1_7':'j_98',
        'mobilegrid':'j_99',
        'mobilegridcell_c977':'j_100',
        'mobilebutton1_13':'j_101',
        'mobilegridcell_c983':'j_102',
        'mobilebutton1_14':'j_103'
    };
    if ("n2id" in window && window.n2id !== undefined) {
        $.extend(n2id, n2id_buf);
    } else {
        window.n2id = n2id_buf;
    }
    Tiggr.CurrentScreen = 'j_95';
    /*************************
     * NONVISUAL COMPONENTS  *
     *************************/
    var datasources = [];
    /************************
     * EVENTS AND HANDLERS  *
     ************************/
    j_95_beforeshow = function () {
        Tiggr.CurrentScreen = 'j_95';
        for (var idx = 0; idx < datasources.length; idx++) {
            datasources[idx].__setupDisplay();
        }
    }
    // screen onload
    screen_C917_onLoad = j_95_onLoad = function () {
        screen_C917_elementsExtraJS();
        j_95_deviceEvents();
        j_95_windowEvents();
        screen_C917_elementsEvents();
    }
    // screen window events
    screen_C917_windowEvents = j_95_windowEvents = function () {
        $('#j_95').bind('pageshow orientationchange', function () {
            adjustContentHeightWithPadding();
        });
    }
    // device events
    j_95_deviceEvents = function () {
        document.addEventListener("deviceready", function () {
            var pushNotification = window.plugins.pushNotification;
            pushNotification.registerDevice({
                alert:true,
                badge:true,
                sound:false,
                senderId:''
            }, function (status) {
                $(document).trigger('pushinit', status);
            });
        });
    }
    // screen elements extra js
    screen_C917_elementsExtraJS = j_95_elementsExtraJS = function () {
        // modalPanel (screen-C917) extra code
    }
    // screen elements handler
    screen_C917_elementsEvents = j_95_elementsEvents = function () {
        $("a :input,a a,a fieldset label").live({
            click:function (event) {
                event.stopPropagation();
            }
        });
    }
    $("#j_95").die("pagebeforeshow").live("pagebeforeshow", function (event, ui) {
        j_95_beforeshow();
    });
    if (runBeforeShow) {
        j_95_beforeshow();
    } else {
        j_95_onLoad();
    }
}
$("#j_95").die("pageinit").live("pageinit", function (event, ui) {
    j_95_js();
});