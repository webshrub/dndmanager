<ul data-role="listview" id="listview" class=' mobilelist3 ' name="mobilelist1_19" dsid="mobilelist1_19"
    data-filter-placeholder='' data-theme='a' data-divider-theme='b' data-count-theme='b'
    style="" data-inset="true" data-split-theme="d" data-split-icon="delete">
    {{#messages}}
    <li>
        <a>
            <fieldset name="fs" data-role="controlgroup">
                <input type="checkbox" name="sms" id="sms"/>
                <label for="sms">
                    <h3>{{name}}</h3>
                    <p><strong>{{number}}</strong></p>
                    <p>{{text}}</p>
                </label>
            </fieldset>
        </a>
        <a name="reportDialogLink" data-rel="dialog" data-transition="pop" data-number="{{number}}" data-date="{{date}}" data-text="{{text}}">Report this.</a>
    </li>
    {{/messages}}
</ul>
