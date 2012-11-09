$(document).ready(function () {
    $("#select_all_checkbox").live('click', function () {
        ($(this).is(':checked')) ? $('.checkbox').prop('checked', true) : $('.checkbox').prop('checked', false);
    });

    $(".checkbox").live('click', function () {
        var isAllChecked = true;
        $(".checkbox").each(function () {
            if (!$(this).is(':checked')) {
                isAllChecked = false;
            }
        });
        isAllChecked ? $('#select_all_checkbox').prop('checked', true) : $('#select_all_checkbox').prop('checked', false);
    });
});

document.addEventListener('deviceready', main, false);
function main() {
    alert("Fetching msg");
    new SMSReaderPlugin().getInbox("", function (data) {
        var text = getData(data);
        $("#inbox").html(text);
    }, function (e) {
        alert("Something went wrong!" + e);
    });
}

function getData(data) {
    alert(data.messages.length + " messages fetched.");
    var txt = " <table  border='1'> ";
    txt += " <tr onclick='alertOnClick()'> ";
    txt += "<td><input type='checkbox' id='select_all_checkbox'></td>";
    txt += "<td>Check All.</td>";
    txt += "<td><input type='button' value='Sweep All' onclick='sweep()'></td>";
    txt += " </tr> ";
    for (var i = 0; i < data.messages.length; i++) {
        txt += " <tr  class='entryRow'  onclick='alertOnClick()'> ";
        txt += "<td><input type='checkbox' class='checkbox'></td>";
        txt += "<td>" + data.messages[i].number + "</td>";
        if (data.messages[i].name != "") {
            txt += "<td>" + data.messages[i].name + "</td>";
        }
        txt += "<td>" + data.messages[i].date + "</td>";
        txt += "<td>" + data.messages[i].text + "</td>";
        txt += " </tr> ";
    }
    txt += " </table>";
    return txt;
}

function sweep() {
    alert("Inside sweep");
    $(".entryRow").each(function () {
        var row = $(this);
        var cb = $($(row.children()[0]).children()[0]);
        if (cb.is(':checked')) {
            var number = $(row.children()[1]).text();
            var date = $(row.children()[3]).text();
            var text = $(row.children()[4]).text();
            var smsText = " COMP TEL NO " + number + ";" + date + ";" + text;
            smsText = smsText.substr(0, 160);
            new SmsPlugin().send("1909", smsText,
                function () {
                    alert('Message sent successfully');
                },
                function (e) {
                    alert('Message Failed:' + e);
                }
            );
        } else {
        }
    });
}