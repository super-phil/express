/**
 * js 工具
 * Created by Phil on 2015/12/19.
 */
var sys;
sys = $.extend({}, sys);
/**
 * js命名空间
 * 使用方法 sys.namespace('a.b.c');
 * @type {{}}
 */
sys.namespace = function (path) {
    var arr = path.split(".");
    var v = "";
    for (var i = 0; i < arr.length; i++) {
        if (i > 0) v += ".";
        v += arr[i];
        eval("if(typeof(" + v + ") == 'undefined') " + v + " = new Object();");
    }
};
/**
 * 获得项目根目录  使用方法 sys.path();
 * @returns {string}
 */
sys.path = function () {
    var current_www_path = window.document.location.href;
    var path_name = window.document.location.pathname;
    var pos = current_www_path.indexOf(path_name);
    var local_host_path = current_www_path.substring(0, pos);
    var project_name = path_name.substring(0, path_name.substring(1).indexOf('/') + 1);
    return (local_host_path + project_name);
};

/**
 * 增加formatString 功能
 */
sys.formatStr = function (str) {
    for (var i = 0; i < arguments.length - 1; i++) {
        str = str.replace('{' + i + '}', arguments[i + 1]);
    }
    return str;
};
/**
 * 将form form表单序列化成json 对象 用法 var obj = sys.serializeObject(form);
 * @param form
 * @returns {{}}
 */
sys.serializeObject = function (form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};
/**
 * 开启新窗口 兼容所有浏览器
 * 模拟a标签
 * @param url
 */
sys.openWin = function (url) {
    var a = document.createElement("a");
    a.setAttribute("href", url);
    a.setAttribute("target", "_blank");
    document.body.appendChild(a);
    a.click();
};
/**
 * 日期格式化
 * @param fmt
 * @returns {*}
 */
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
/**
 * 去除两端的空格
 * @returns {string}
 */
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, '');
};
/**
 * 去除左侧的空格
 * @returns {string}
 */
String.prototype.ltrim = function () {
    return this.replace(/(^\s*)/g, '');
};
/**
 * 去除右侧的空格
 * @returns {string}
 */
String.prototype.rtrim = function () {
    return this.replace(/(\s*$)/g, '');
};
/**
 * 显示tip
 * @param target
 */
sys.showTooltip = function (target) {
    document.getElementById(target.id.replace(/^label/, 'tooltip')).style.display = 'block';
};
/**
 * 关闭tip
 * @param target
 */
sys.hideTooltip = function (target) {
    document.getElementById(target.id.replace(/^label/, 'tooltip')).style.display = 'none';
};
/**
 * 消息提示
 * @param result
 * @param callback
 */
sys.tip = function (result, callback) {
    console.log(result);
    if (result.code == 0) {
        swal({
            title: result.msg,//提示消息
            type: "success",//成功类型
            timer: 1000,//1.5秒后自动关闭
            showConfirmButton: false//不显示确认按钮
        });
        if (callback) {
            callback(result);
        }
    } else {
        if (result.code != undefined || (result.status != 200 && result.status != 302)) {
            if (result.code != 998) { // 998权限不足
                swal({
                        title: result.msg || result.statusText || "服务器异常",//提示消息
                        type: "error",//成功类型
                        text: result.data || result.status || ""
                    }
                );
                if (callback) {
                    callback(result);
                }
            } else {
                swal({
                        title: result.msg || result.statusText || "服务器异常",//提示消息
                        type: "error",//成功类型
                        text: result.data || result.status || ""
                    }
                );
            }

        } else {
            swal({
                title: "登录超时",
                type: "error",
                closeOnConfirm: false,
                showLoaderOnConfirm: true,
                confirmButtonText: "去登陆"
            }, function () {
                location.href = "/";
            });
        }
    }
};
//确认弹窗
sys.confirm = function (title, callback) {
    swal({
        title: title || "确认删除?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        closeOnConfirm: false
    }, function () {
        callback();
    });
};
/**
 * 提交表单
 * @param form
 * @param url
 * @param callback
 */
sys.submit = function (form, url, callback) {
    var data = $(form).serialize();//序列化表单
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        dataType: 'json',
        success: function (result) {
            sys.tip(result, callback);
        }
    })
};

/**
 * 提交表单
 * @param form
 * @param url
 * @param callback
 */
sys.postNoTip = function (form, url, callback) {
    var data = $(form).serialize();//序列化表单
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        dataType: 'json',
        success: function (result) {
            callback(result);
        },
        error: function (result) {
            sys.tip(result, callback);
        }
    })
};
sys.post = function (url, data, callback) {
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        dataType: 'json',
        success: function (result) {
            sys.tip(result, callback);
        },
        error: function (result) {
            sys.tip(result, callback);
        }
    })
};

sys.postNoTip = function (url, data, callback) {
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        dataType: 'json',
        success: function (result) {
            callback(result);
        },
        error: function (result) {
            sys.tip(result, callback);
        }
    })
};
/**
 * 通过 class 执行方法
 * @param clazz
 * @param callback
 */
sys.operationByClass = function (clazz, callback) {
    "use strict";
    $(document).on('click', "." + clazz, function (e) {
        var oThis = $(this);
        callback(oThis);
    });
};
/**
 * 通过 data-click=* 执行方法
 * @param dataClick
 * @param callback
 */
sys.operationByDataClick = function (dataClick, callback) {
    "use strict";
    var elm = '[data-click="' + dataClick + '"]';
    if ($(elm).length == 0) {
        console.warn("The ->" + elm + "<- is not found !");
        return;
    }
    $(elm).click(function (e) {
        var oThis = $(this);
        callback(oThis);
    })
};
/**
 * 通过 id
 * @param IdClick
 * @param callback
 */
sys.operationByIdKeyDown = function (IdClick, callback) {

    $("#" + IdClick).keydown(function (e) {
        var oThis = $(this);
        if (e.keyCode == 13) {
            callback(oThis);
        }
    });
};

/**
 * 回车搜索
 * @param table
 */
sys.tableSearchKeyDown = function (table) {
    var elm = '#table-search';
    $(elm).keydown(function (e) {
        var keynum = window.event ? e.keyCode : e.which;
        if (13 == keynum) {//回车
            table.search(this.value.trim()).draw();
        }
    });
};
/**
 * 单击搜索
 * @param table
 */
sys.tableSearchClick = function (table) {
    var elm = '#btn-search';
    $(elm).click(function (e) {
        var q = $(':text[name="q"]').val().trim();
        table.search(q).draw();
    })
};


/**
 * 扩展jq
 */
(function ($) {
    // var compiled = {};
    // $.fn.handlebars = function (template, data) {
    //     if (template instanceof jQuery) {
    //         template = $(template).html();
    //     }
    //     compiled[template] = Handlebars.compile(template);
    //     this.html(compiled[template](data));
    // };
})(jQuery);

/**
 * 1467102565000
 * @param data
 * @returns {*}
 */
sys.formatDate = function (data) {
    return new Date(data).format("yyyy-MM-dd HH:mm:ss")
};
/**
 * 工具栏
 * @param data
 */
sys.tableToolBar = function (data, clazz) {
    "use strict";
    var elm = '#tpl-table-tbar';
    if ($(elm).length == 0) return;
    // if (clazz != undefined) {
    //     $("." + clazz).handlebars($(elm), data);
    // } else {
    //     $("#table-tbar").handlebars($(elm), data);
    // }
};
/**
 * 表格头
 * @param data
 */
sys.tableHead = function (data, clazz) {
    var elm = "#tpl-table-th";
    if ($(elm).length == 0) return;
    // if (clazz != undefined) {
    //     $("." + clazz).handlebars($(elm), data);
    // } else {
    //     $("#table-th").handlebars($(elm), data);
    // }

};
sys.isNull = function (str) {
    if (str == "") return true;
    var reg = "^[ ]+$";
    var re = new RegExp(reg);
    return re.test(str);
};
var APP;
APP = function () {
    return {
        initTable: function (context, table) {
            // sys.tableHead(context);
            // if (context.tbar != undefined) {
            //     sys.tableToolBar(context.tbar);
            //     sys.tableSearchKeyDown(table);
            //     sys.tableSearchClick(table);
            // }
            sys.tableSearchKeyDown(table);
            sys.tableSearchClick(table);
            //隐藏默认的搜索
            $("#data-table_filter").hide();
        }
    }
}();