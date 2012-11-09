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

document.addEventListener('deviceready', fetchCallLog, false);
function alertOnClick() {
//    alert("Add Action.");
}

function epochToJsDate(ts) {
    return new Date(ts);
}
function fetchCallLog() {
    alert("Fetching call log");
    new CallLogPlugin().list('week',
        function (data) {
            alert(data.rows.length + " logs Fetched");
            var txt = " <table  border='1'> ";
            txt += " <tr onclick='alertOnClick()'> ";
            txt += "<td><input type='checkbox' id='select_all_checkbox'></td>";
            txt += "<td>Check All.</td>";
            txt += "<td><input type='button' value='Sweep All' onclick='sweep()'></td>";
            txt += " </tr> ";
            for (var i = 0; i < data.rows.length; i++) {
                txt += " <tr class='entryRow' onclick='alertOnClick()'> ";
                txt += "<td><input type='checkbox' class='checkbox'></td>";
                if (data.rows[i].cachedName != "") {
                    txt += "<td>" + data.rows[i].cachedName + "</td>";
                }
                txt += " <td>" + data.rows[i].number + "</td> ";
                txt += " <td> " + data.rows[i].date + "</td> ";
                txt += " </tr> ";
            }
            txt += " </table>";
            $("#logs").html(txt);
        },
        function (e) {
            alert('Fetching of list failed.' + e);
        }
    );
}

function sweep() {
    $(".entryRow").each(function () {
        var row = $(this);
        var cb = $($(row.children()[0]).children()[0]);
        if (cb.is(':checked')) {
            var number = $(row.children()[2]).text();
            var date = $(row.children()[3]).text();
            var smsText = " COMP TEL NO " + number + ";" + date + ";" + "Unknown company";
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