$(document).ready(function () {
    $("#oldpassword").on("blur",function(){
        var oldpassword=$("#oldpassword").val();
        /*alert(oldpassword);*/

        $.ajax({
            type:"GET",//请求类型
            url:"/thymeleaf/queryAndEdit/queryOldPassword",//请求的url
            data:{oldpassword:oldpassword},//请求参数
            dataType:"json",//ajax接口（请求url）返回的数据类型
            success:function(data){//data：返回数据（json对象）
                if(data.msg == "success"){
                    alert("密码正确！");
                    $("#newpassword").removeAttr("readonly");
                }else if(data.msg == "error"){
                    alert("密码错误");
                }
            },
            error:function(data){
                alert("请求错误");
            }
        });

    });

})

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