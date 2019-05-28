$(function(){
    $("tbody tr:even").css("background","blanchedalmond");

    $("tr").mouseover(function(){
        $(this).css("background","aqua");
    });

    $("tr").mouseout(function(){
        $("tbody tr:even").css("background","blanchedalmond");
        $("tbody tr:odd").css("background","white");
    })
});

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

$(document).ready(function(){
    $("#table_id").DataTable();

    $(".processed").on("click",function(){
        var obj = $(this).parents("tr").children(0);
        var account = obj.attr("id");
        window.location.href = "/thymeleaf/queryAndEdit/modifyCig?account="+account;
    });

});


function del()
{
    if(confirm("确定要删除吗？"))
    {
        return true;
    }
    else
    {
        return false;
    }

}