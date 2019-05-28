$(".upd").bind("click", function () {
    var s = confirm("您确定取消吗？")
    if (s == true) {
        //取消在职状态
        var account = $(this).parent().parents().children(":eq(0)").html();
        var location = $(this).parent().parents().children(":eq(4)").html();
        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            async: true,
            data: {"account": account, "location": location},
            url: "/thymeleaf/backAdminIndex/update",
            dataType: "json",
            success: function (data) {
                if (data.zt == "ok") {
                    alert("操作成功");
                    window.location.href = "/thymeleaf/backAdminIndex/user";
                    return;
                }
                if (data.zt == "no") {
                    alert("操作失败");
                    return;
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        })
    } else {
        return;
    }
})

$(".upd1").bind("click", function () {
    var s = confirm("您确定恢复吗？")
    if (s == true) {
        //恢复在职状态
        var account = $(this).parent().parents().children(":eq(0)").html();
        var location = $(this).parent().parents().children(":eq(4)").html();
        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            async: true,
            data: {"account": account, "location": location},
            url: "/thymeleaf/backAdminIndex/update1",
            dataType: "json",
            success: function (data) {
                if (data.zt == "ok") {
                    alert("操作成功");
                    window.location.href = "/thymeleaf/backAdminIndex/user";
                    return;
                }
                if (data.zt == "no") {
                    alert("操作失败");
                    return;
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        })
    } else {
        return;
    }
})