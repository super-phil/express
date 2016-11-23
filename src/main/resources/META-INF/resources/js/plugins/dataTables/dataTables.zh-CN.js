$.extend($.fn.dataTable.defaults, {
    // "stateSave":true,//是否持久化保存前台状态
    "searching": true,//不允许搜索
    "lengthChange": false,//不允许改变每页显示数量
    "processing": false,
    "serverSide": true,//服务器交互
    "ordering": false,//排序
    "autoWidth": true,//自动宽度
    "deferRender": true,
    "scrollX": true,//水平滚动条
    "language": {
        "sSearch": "查询:",
        "lengthMenu": "每页 _MENU_ 条记录",
        "zeroRecords": "没有找到记录",
        "sEmptyTable": "没有找到记录",
        "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
        "infoEmpty": "没有找到记录",
        "infoFiltered": "(从 _MAX_ 条记录过滤)",
        "sProcessing": "加载中... ...",
        "paginate": {
            "previous": "上一页",
            "next": "下一页"
        }
    }
});