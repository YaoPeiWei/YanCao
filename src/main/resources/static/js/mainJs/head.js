$("#tousu").click(function () {
    var s = confirm("您确定提交吗？")
    if(s == true){
        var report = $("#report").val()
        $.ajax({
            url:"/thymeleaf/Report/CheckChar",
            dataType: "json",
            async:true,
            data:{"report":report},
            type:"GET",
            success:function(map){
                if(map.data.length==0){
                    // $(this).attr("action","/thymeleaf/Report/InsertReport")
                    window.location.href="/thymeleaf/Report/InsertReport?report="+report;

                }
                if(map.data.length>0){
                    var s="你的举报中包含：";
                    $.each(map.data,function(i,a){
                        s=s+a;
                        if(i<map.data.length-1){
                            s+=",";
                            i++;
                        }
                    });
                    s+="等敏感词汇，请重新修改一遍";
                    $("#ciyu").css("display","block")
                    $("#report").focus();
                }
            }
        });
    }

})