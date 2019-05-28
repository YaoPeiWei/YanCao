function jc() {
    var pwd = $("#regpassword1").val();
    var pwd1 = $("#regpassword").val();
    if(pwd == pwd1)
    {
        $("#green").show();
        $("#red").hide()
    }
    else {
        $("#red").show();
        $("#green").hide()
    }
}

$(function () {

    $("#regsubmit").click(function () {
        var accounts = $("#userid").val();
        var password = $("#regpassword").val();
        var password1 = $("#regpassword1").val();
        var phone = $("#regphone").val();
        var location = $("#reglocation").val();
        if(accounts == "" || password1=="" || password == "" || phone == "" ){
            alert("信息不能为空")
            return;
        }
        if(password != password1){
            alert("两次密码不一致")
            return;
        }
        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            async: true,
            data: {"name": accounts, "password": password,
                "phone": phone,"location": location},
            url: "/thymeleaf/userAdd/add",
            dataType: "json",
            success: function (data) {
                if (data.zt == "ok") {
                    alert("注册成功");
                    window.location.href = "/thymeleaf/userIndex/html";
                    return;
                }
                if (data.zt == "name") {
                    alert("该账户已被注册");
                    return;
                }
                if (data.zt == "phone") {
                    alert("手机号码已被注册");
                    return;
                }

                if (data.zt == "no") {
                    alert("未知错误");
                    return;
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        })
    })
});

function back() {
    $("#zhuce").css("display","none")
    $("#hidebg").css("display","none");
}