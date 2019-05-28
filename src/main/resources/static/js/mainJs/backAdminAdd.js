var pit = null;
function showPreview(source) {
    var file = source.files[0];
    if(window.FileReader) {
        var fr = new FileReader();
        var portrait = document.getElementById('p');
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
$(function () {

    $("#submit").click(function () {
        var accounts = $("#accounts").val();
        var c = accounts.charAt(0);
        var name = $("#name").val();
        var password = $("#password").val();
        var phone = $("#phone").val();
        var img = $("#p").val();
        var location = $("#location").val();
        if( !(c>=0&&c<=3)){
            alert("区域编号不存在")
            return;
        }
        if(accounts == "" || name == "" || password == "" || phone == "" ){
            alert("信息不能为空")
            return;
        }
        if(c!==location){
            alert("账户区域地址与所选工作地区不一致")
            return;
        }
        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            async: true,
            data: {"account": accounts,"name": name, "password": password,
                "phone": phone,"sex": $("#sex").val(),"location": location,"img": pit,},
            url: "/thymeleaf/backAdminAdd/add",
            dataType: "json",
            success: function (data) {
                if (data.zt == "ok") {
                    alert("业务员添加成功");
                    window.location.href = "/thymeleaf/backAdminIndex/user";
                    return;
                }
                if (data.zt == "no") {
                    alert("业务员添加失败");
                    return;
                }
                if (data.zt == "zai") {
                    alert("业务员账户ID已存在");
                    return;
                }

                if (data.zt == "error") {
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
function jc() {
    var pwd = $("#password1").val();
    var pwd1 = $("#password").val();
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
