$(document).ready(function () {
    $(".addorder").bind("click", function(){
        //用异步将相关的香烟信息添加到order表中
        var t=$(this)
        var  account= $(this).parents("tr").children("td:nth-child(1)").text()
        var  name= $(this).parents("tr").children("td:nth-child(2)").text()
        var  surplus= $(this).parents("tr").children("td:nth-child(3)").text()
        $.ajax({
            url:"/thymeleaf/Order/AddOrder",
            dataType: "json",
            async:true,
            data:{"account":account,"cigaretee":name,"number":surplus},
            type:"GET",
            success:function(map){
                if(map.data==("success")){
                    alert("success");
                    //t.unbind("click");
                }
                else alert("fail");
            }
        });
    });
})