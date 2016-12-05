<script id="tpl-table-tbar" type="text/x-handlebars-template">
    <div class="tbar-left col-lg-9 col-md-8 col-sm-6 col-xs-6">
        {{#btn}}
        <button type="button" {{#if id}} id="{{id}}" {{/if}} class="{{#if class}}{{class}}{{/if}} btn btn-sm btn-{{style}} m-r-5 m-b-5" data-click="{{fn}}">{{name}}</button>
        {{/btn}}
    </div>
    {{#if search}}
    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-6">
        <div class="input-group input-group-sm">
            <input type="text" name="q" class="form-control" data-click="table-tbar-search" placeholder="{{#if search.tips}} {{search.tips}} {{else}} search {{/if}}"
                   autocomplete="off">
            <div class="input-group-btn">
                <button type="button" class="btn btn-default" data-click="btn-table-tbar-search"><i
                        class="fa fa-search"></i>
                </button>
            </div>
        </div>
    </div>
    {{/if}}
</script>