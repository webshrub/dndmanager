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
    txt += "<td>Check<br> All.</td>";
    txt += " </tr> ";
    for (var i = 0; i < data.messages.length; i++) {
        txt += " <tr onclick='alertOnClick()'> ";
        txt += "<td><input type='checkbox' class='checkbox'></td>";
        txt += "<td><bs>Number:</b>" + data.messages[i].number + "</td>";
        if (data.messages[i].name != "") {
            txt += "<td><b>Name:</b>" + data.messages[i].name + "</td>";
        }
        txt += "<td><b> Message:</b>" + data.messages[i].text + "</td>";
        txt += " </tr> ";
    }
    txt += " </table>";
    ;
    return txt;
}