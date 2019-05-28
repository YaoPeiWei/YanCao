$(function () {
    $("#submit").click(function () {
        var account = $("#account").val();
        var c = account.charAt(0);
        var password = $("#password").val();
        if(account == "" || password == ""){
            alert("账户信息不能为空");
            return;
        }
        if( !(c>=0&&c<=3)){
            alert("区域编号不存在")
            return;
        }

        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            async: true,
            data: {"account": $("#account").val(), "password": $("#password").val()},
            url: "/thymeleaf/backAdminLogin/login",
            dataType: "json",
            success: function (data) {
                if (data.zt == "noName") {
                    alert("账户不存在");
                    return;
                }
                if (data.zt == "noService") {
                    alert("无登陆权限");
                    return;
                }
                if (data.zt == "noPass") {
                    alert("密码错误");
                    return;
                }
                if (data.zt == "ok") {
                    window.location.href = "/thymeleaf/backAdminLogin/index";
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