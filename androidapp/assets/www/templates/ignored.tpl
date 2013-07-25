<ul data-role="listview" id="callListView" class=' mobilelist1 ' name="mobilelist1_17" dsid="mobilelist1_17"
    data-filter-placeholder='' data-theme='a' data-divider-theme='b' data-count-theme='b'
    style="" data-inset="true" data-split-theme="d" data-split-icon="delete">
    {{#rows}}
        <li>
            <a>
                <fieldset name="fs" data-role="controlgroup">
                    <input type="checkbox" name="ignored" id="ignored"/>
                    <label for="ignored">
                        <h3>{{cachedName}}</h3>
                        <p><strong>{{number}}</strong></p>
                    </label>
                </fieldset>
            </a>
            <a name="removeIgnoredDialogLink" data-rel="dialog" data-transition="pop" data-number="{{number}}" data-id="{{id}}">Remove this.</a>
        </li>
    {{/rows}}
</ul>
