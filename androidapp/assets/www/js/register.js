/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
$(document).ready(function () {
    $("#select_all_checkbox").change(function () {
        ($(this).is(':checked')) ? $('.checkbox').prop('checked', true) : $('.checkbox').prop('checked', false);
    });

    $(".checkbox").change(function () {
        var isAllChecked = true;
        $(".checkbox").each(function () {
            if (!$(this).is(':checked')) {
                isAllChecked = false;
            }
        });
        isAllChecked ? $('#select_all_checkbox').prop('checked', true) : $('#select_all_checkbox').prop('checked', false);
    });
});

function register() {
    var smsText = "START ";
    var isFirst = true;
    $(':checkbox').each(function () {
        if ($(this).is(':checked')) {
            if (isFirst) {
                smsText = smsText + $(this).val();
                isFirst = false;
            } else {
                smsText = smsText + "," + $(this).val();
            }
        }
    });
    new SmsPlugin().send('1909', smsText,
        function () {
            alert('Message sent successfully');
        },
        function (e) {
            alert('Message Failed:' + e);
        }
    );
}

function unregister() {
    var smsText = "STOP ";
    var isFirst = 'true';
    $(':checkbox').each(function () {
        if ($(this).is(':checked')) {
            if (isFirst) {
                smsText = smsText + $(this).val();
                isFirst = false;
            } else {
                smsText = smsText + "," + $(this).val();
            }
        }
    });
    new SmsPlugin().send('1909', smsText,
        function () {
            alert('Message sent successfully');
        },
        function (e) {
            alert('Message Failed:' + e);
        }
    );
}