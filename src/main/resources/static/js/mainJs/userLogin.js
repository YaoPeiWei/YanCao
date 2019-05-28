
    $("#submit").click(function () {
        var name = $("#name").val();
        var password = $("#password").val();
        if(name == "" || password == ""){
            alert("账户信息不能为空");
            return;
        }
        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            async: true,
            data: {"name": name, "password": password},
            url: "/thymeleaf/userIndex/user",
            dataType: "json",
            success: function (data) {
                if (data.zt == "noName") {
                    alert("账户不存在");
                    return;
                }
                if (data.zt == "noPass") {
                    alert("密码错误");
                    return;
                }
                if (data.zt == "ok") {
                    window.location.href = "/thymeleaf/Cigarette/SelectCigarette";
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

    $("#zhuce").load("/thymeleaf/userAdd/html")

    function reg() {
        $("#zhuce").css("display","block")
        $("#hidebg").css("display","block");
    }

    function forget() {
        // alert("功能暂未开放")
        window.location.href = "/thymeleaf/userIndex/findPassword";

    }
