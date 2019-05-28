$(document).ready(function () {
    var messageOpts = {
        "closeButton" : true,//是否显示关闭按钮
        "debug" : false,//是否使用debug模式
        "positionClass" : "toast-bottom-right",//弹出窗的位置
        "onclick" : null,
        "showDuration" : "300",//显示的动画时间
        "hideDuration" : "1000",//消失的动画时间
        "timeOut" : "5000",//展现时间
        "extendedTimeOut" : "1000",//加长展示时间
        "showEasing" : "swing",//显示时的动画缓方式
        "hideEasing" : "linear",//消失时的动画缓冲方式
        "showMethod" : "fadeIn",//显示时的动画方式
        "hideMethod" : "fadeOut" //消失时的动画方式
    };
    toastr.options = messageOpts;
    var totalprice = parseFloat($("#totalprice").val());
    $("#commit").bind("click",function () {
        var money=$("#totalprice").val();
        var address = $("#address").val();
        var phone = $("#phone").val();
        if(address=="" || address==null){
            //alert("收获地址不能为空")
            toastr.warning('订单', '收获地址不能为空');
            $("#address").focus();
            return;
        }
        else if(phone=="" || phone==null){
            //alert("电话不能为空")
            toastr.warning('订单', '电话不能为空');
            $("#phone").focus();
            return;
        }else{
            //window.open("/thymeleaf/UserBuy/pay?address="+address+"&phone="+phone+"&money="+money,'_blank','width=500,height=400,menubar=no,toolbar=no,status=no,scrollbars=yes')
            window.location.href="/thymeleaf/UserBuy/pay?address="+address+"&phone="+phone+"&money="+money;
            //window.location.href="/thymeleaf/UserBuy/UpdateUserBuy?address="+address+"&phone="+phone;
        }
    })
    $(".addnumber").bind("click", function(){
        //用异步将相关的香烟数量信息更新到order表中
        var t=$(this);
        var  number= parseInt($(this).parents("tr").children("td:nth-child(2)").text())+1;
        var order=$(this).parents("tr").children("td:nth-child(4)").text();
        var price = $(this).parents("tr").children("td:nth-child(5)").text();
        var cigarette=$(this).parents("tr").children("td:nth-child(3)").text();
        var n=$(this).parents("tr").children("td:nth-child(2)");
        //alert(number)
        $.ajax({
            url:"UpdateCigaretteNumber",
            dataType: "json",
            async:true,
            data:{"number":number,"order":order,"cigarette":cigarette},
            type:"GET",
            success:function(map){
                if(map.data==("success")){
                    n.text(number);
                    //$("#totalprice").html(totalprice+=parseFloat(price))
                    //alert(totalprice+=parseFloat(price))
                    $("#totalprice").val(totalprice+=parseFloat(price))
                } else {
                    toastr.error('订单', '增加数量失败');
                }
            }
        });
    });


    $(".reducenumber").bind("click", function(){
        //用异步将相关的香烟数量信息更新到order表中
        totalprice = parseFloat($("#totalprice").val());
        var t=$(this);var p=$(this).parents("tr");
        var  number= parseInt($(this).parents("tr").children("td:nth-child(2)").text())-1;
        var order=$(this).parents("tr").children("td:nth-child(4)").text();
        var cigarette=$(this).parents("tr").children("td:nth-child(3)").text();
        var price = $(this).parents("tr").children("td:nth-child(5)").text();
        var n=$(this).parents("tr").children("td:nth-child(2)");
        //alert(number)
        if(number==0){
            var r=confirm("将会移除该香烟");
            if(r){
                $.ajax({
                    url:"DeleteOrderCigarette",
                    dataType: "json",
                    async:true,
                    data:{"order":order,"cigarette":cigarette},
                    type:"GET",
                    success:function(map){
                        if(map.data==("success")){
                            //alert(totalprice)
                            p.remove();
                            //p.css("{ type:hidden }");
                            if(map.length=="0"){
                                $("#you").remove();
                                $("#context").append( " <div th:id=\"wu\"><p>无订单信息</p></div>")
                            }else{
                                // $("#totalprice").html(totalprice-=parseFloat(price))
                                $("#totalprice").val(totalprice-=parseFloat(price))
                            }
                        } else{
                            toastr.error('订单', '移除失败');
                        }
                    }
                });
            }
            return ;
        }
        $.ajax({
            url:"UpdateCigaretteNumber",
            dataType: "json",
            async:true,
            data:{"number":number,"order":order,"cigarette":cigarette},
            type:"GET",
            success:function(map){
                if(map.data==("success")) {
                    n.text(number);
                    //$("#totalprice").html(totalprice-=parseFloat(price))
                    $("#totalprice").val(totalprice-=parseFloat(price))
                } else {
                    toastr.error('订单', '减少数量失败');
                }
            }
        });
    });
})