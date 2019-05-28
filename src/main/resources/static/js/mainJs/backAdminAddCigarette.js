<!--检查input内容是否为空-->
function CheckNull() {
    var num = 0;
    $("input[type$='text']").each(function (n)
    {
        if ($(this).val() == "")
        {
            num++;
        }

    });
    $("input[type$='number']").each(function (n)
    {
        if ($(this).val() == "")
        {
            num++;
        }

    });
    if (num > 0)
    {
        return true;
    } else {
        return false;
    }
}
<!--ajax异步获取account,检查account是否为空-->
$(function(){
    //限制account的字段长度
    $("#account1").attr({maxlength:"6"});
    $("#account1").blur(function () {
        $.ajax({
            type: "GET",
            url: "check",
            data: {account: $("#account1").val()},
            dataType: "json",
            success: function (data) {
                if (data.account == "empty") {
                    $("#tip").html("账户不能为空！");
                    alert("账号不能为空");
                }else if(data.account=="exist"){
                    $("#tip").html("账户已经存在！");
                    $("#sub").submit(false);
                    $("#sub").attr("disabled",true);
                }else if(data.account=="noexist"){
                    $("#tip").html("账户可以使用");
                    $("#sub").attr("disabled",false);
                }
            },
            error: function () {
                alert("服务器端异常");
            }
        })
    })
    $("form").submit(function (e)
    {
        if (CheckNull()){
            e.preventDefault();
            //$("#ErrorMessage").text("不能有空项！");
            alert("不能有空项！")
        }
    })

})

var pit = null;
function showPreview(source) {
    var file = source.files[0];
    if(window.FileReader) {
        var fr = new FileReader();
        var portrait = document.getElementById('img');
        fr.onloadend = function(e) {
            portrait.src = e.target.result;
        };
        fr.readAsDataURL(file);
        fr.onload=function (ev) {
            ev.target.result;
            pit = ev.target.result;
        }
        portrait.style.display = 'block';
    }
}