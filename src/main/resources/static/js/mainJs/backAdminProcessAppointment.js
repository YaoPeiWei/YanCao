$(document).ready(function(){
    $("#table_id").DataTable();


    $(".processed").on("click",function(){
        var obj = $(this).parents("tr").children(0);

        // var orderObj =  $(this).parents("tr").children(5);
        var orderObj=$(this).parents("tr").children("td.o");
        // var user = obj.attr("id");
        // alert(user);
        var user=obj.attr("id");
        var order=orderObj.attr("id");
        // var order=obj.last().attr("id");
        // alert(user);
        // alert(order);


        window.location.href = "/thymeleaf/queryAndEdit/processed?user="+user+"&order="+order;
        /*$.ajax({
            type:"GET",//请求类型
            url:"/thymeleaf/queryAndEdit/processed",//请求的url
            data:{user:user},//请求参数
            dataType:"json",//ajax接口（请求url）返回的数据类型
            success:function(data){//data：返回数据（json对象）
                if(data.msg == "success"){
                    alert("已處理！");
                    $("#newpassword").removeAttr("readonly");
                }else if(data.msg == "error"){
                    alert("發生错误");
                }
            },
            error:function(data){
                alert("请求错误");
            }
        });*/
    });
})

$(function(){
    $("tbody tr:even").css("background","blanchedalmond");

    $("tr").mouseover(function(){
        $(this).css("background","aqua");
    });

    $("tr").mouseout(function(){
        $("tbody tr:even").css("background","blanchedalmond");
        $("tbody tr:odd").css("background","white");
    })
});