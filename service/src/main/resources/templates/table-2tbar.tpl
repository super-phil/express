<script id="tpl-table-tbar" type="text/x-handlebars-template">
    <div class="tbar-left col-lg-6 col-md-4 col-sm-6 col-xs-6">
        {{#btn}}
        <button type="button" {{#if id}} id="{{id}}" {{/if}} class="{{#if class}}{{class}}{{/if}} btn btn-sm btn-{{style}} m-r-5 m-b-5" data-click="{{fn}}">{{name}}</button>
        {{/btn}}
    </div>
    {{#search}}
    <div class="col-lg-3 col-md-4 col-sm-3 col-xs-3">
        <div class="input-group input-group-sm">
            <input type="text" name="q" size="66" class="q-{{@index}} form-control" id="table-tbar-input-search-{{@index}}" placeholder="{{#if tips}} {{tips}} {{else}} search {{/if}}"
                   autocomplete="off">
        </div>
    </div>
    {{/search}}
</script>