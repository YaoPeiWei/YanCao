// $(document).ready(function () {
//     $(".addorder").bind("click", function(){
//         //用异步将相关的香烟信息添加到order表中
//         var t=$(this)
//         var  account= $(this).parents("tr").children("td:nth-child(1)").text()
//         var  name= $(this).parents("tr").children("td:nth-child(2)").text()
//         var  surplus= $(this).parents("tr").children("td:nth-child(3)").text()
//         $.ajax({
//             url:"thymeleaf/Order/AddOrder",
//             dataType: "json",
//             async:true,
//             data:{"account":account,"cigaretee":name,"number":surplus},
//             type:"GET",
//             success:function(map){
//                 if(map.data==("success")){
//                     alert("添加成功");
//                     //t.unbind("click");
//                 }
//                 else alert("fail");
//             }
//         });
//     });
// })
$(document).ready(function () {
    var messageOpts = {
        "closeButton": true,//是否显示关闭按钮
        "debug": false,//是否使用debug模式
        "positionClass": "toast-bottom-right",//弹出窗的位置
        "onclick": null,
        "showDuration": "300",//显示的动画时间
        "hideDuration": "1000",//消失的动画时间
        "timeOut": "5000",//展现时间
        "extendedTimeOut": "1000",//加长展示时间
        "showEasing": "swing",//显示时的动画缓方式
        "hideEasing": "linear",//消失时的动画缓冲方式
        "showMethod": "fadeIn",//显示时的动画方式
        "hideMethod": "fadeOut" //消失时的动画方式
    };
    toastr.options = messageOpts;
    $("#tousu").click(function () {
        var s = confirm("您确定提交吗？")
        if (s == true) {
            var report = $("#report").val()
            $.ajax({
                url: "/thymeleaf/Report/CheckChar",
                dataType: "json",
                async: true,
                data: {"report": report},
                type: "GET",
                success: function (map) {
                    if (map.data.length == 0) {
                        // $(this).attr("action","/thymeleaf/Report/InsertReport")
                        window.location.href = "/thymeleaf/Report/InsertReport?report=" + report;
                    }
                    if (map.data.length > 0) {
                        var s = "你的举报中包含：";
                        $.each(map.data, function (i, a) {
                            s = s + a;
                            if (i < map.data.length - 1) {
                                s += ",";
                                i++;
                            }
                        });
                        s += "等敏感词汇，请重新修改一遍";
                        $("#ciyu").css("display", "block")
                        $("#report").focus();
                    }
                }
            });
        }

    })
    $(document).on('click', "#addorder", function(){
        //用异步将相关的香烟信息添加到order表中
        var t = $(this)
        var account = $(this).parents("tr").children("td:nth-child(1)").text()
        var name = $(this).parents("tr").children("td:nth-child(2)").text()
        var surplus = $(this).parents("tr").children("td:nth-child(3)").text()
        $.ajax({
            url: "/thymeleaf/Order/AddOrder",
            dataType: "json",
            async: true,
            data: {"account": account, "cigaretee": name, "number": surplus},
            type: "GET",
            success: function (map) {
                if (map.data == ("success")) {
                    //$("#myAlert").show("slow");
                    //alert("添加成功");
                    //t.unbind("click");
                    toastr.success('Success', '添加烟草');
                } else {
                    toastr.warning('Fail', '添加烟草');
                }
            }
        });
    });
    // $("#addorder").bind("click", function () {
    //     //用异步将相关的香烟信息添加到order表中
    //     var t = $(this)
    //     var account = $(this).parents("tr").children("td:nth-child(1)").text()
    //     var name = $(this).parents("tr").children("td:nth-child(2)").text()
    //     var surplus = $(this).parents("tr").children("td:nth-child(3)").text()
    //     $.ajax({
    //         url: "/thymeleaf/Order/AddOrder",
    //         dataType: "json",
    //         async: true,
    //         data: {"account": account, "cigaretee": name, "number": surplus},
    //         type: "GET",
    //         success: function (map) {
    //             if (map.data == ("success")) {
    //                 //$("#myAlert").show("slow");
    //                 //alert("添加成功");
    //                 //t.unbind("click");
    //                 toastr.success('Success', '添加烟草');
    //             } else {
    //                 toastr.warning('Fail', '添加烟草');
    //             }
    //         }
    //     });
    // });
    var count = $('#count').text();
    var index = $('#index').text();
    // alert(index)
    $('#pageLimit').bootstrapPaginator({
        currentPage: index,//当前的请求页面。
        totalPages: count,//一共多少页。
        size: "normal",//应该是页眉的大小。
        bootstrapMajorVersion: 3,//bootstrap的版本要求。
        alignment: "right",
        numberOfPages: 5,//一页列出多少数据。
        itemTexts: function (type, page, current) {//如下的代码是将页眉显示的中文显示我们自定义的中文。
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "末页";
                case "page":
                    return page;
            }//默认显示的是第一页。
        },
        onPageClicked: function (event, originalEvent, type, page) {//给每个页眉绑定一个事件，其实就是ajax请求，其中page变量为当前点击的页上的数字。
            $.ajax({
                url: '/thymeleaf/Cigarette/SelectCigaretteAjax',
                type: 'POST',
                data: {'index': page},
                dataType: 'JSON',
                success: function (data) {
                    $('tbody').empty();
                    // alert(data.cigarettes.length)
                    // alert(data.cigarettes.valueOf())
                    var list=data.cigarettes;
                    for(var i=0;i<data.cigarettes.length;i++){
                        var name=list[i].name;
                        var account=list[i].account;
                        var surplus=list[i].surplus;
                        var img=list[i].img.toString();
                        var cumulative	=list[i].cumulative;
                        var price=list[i].price;
                        //alert(img)
                        $("tbody").append("<tr><td value='"+account+"'>"+account+"</td>" +
                            "<td value='"+name+"'>"+name+"</td>" +
                            "<td><img style=\"width: 40px;height: 40px\" src='/thymeleaf/img/upLoadCigarette/"+img+"'/></td>" +
                            "<td value='"+surplus+"'>"+surplus+"</td>" +
                            "<td value='"+price+"'>"+price+"</td>" +
                            "<td><button class='btn btn-default btn-success addorder' id='addorder'>\n" +
                            "<span class=\"glyphicon glyphicon-plus\"></span> 添加</button></td></tr>")
                    }
                }
            })
        }
    });
})