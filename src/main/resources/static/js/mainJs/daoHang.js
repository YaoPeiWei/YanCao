$(function(){
    // nav收缩展开
    $('.navMenu li a').on('click',function(){
        var parent = $(this).parent().parent();//获取当前页签的父级的父级
        var labeul =$(this).parent("li").find(">ul")
        if ($(this).parent().hasClass('open') == false) {
            //展开未展开
            parent.find('ul').slideUp(300);
            parent.find("li").removeClass("open")
            parent.find('li a').removeClass("active").find(".arrow").removeClass("open")
            $(this).parent("li").addClass("open").find(labeul).slideDown(300);
            $(this).addClass("active").find(".arrow").addClass("open")
        }else{
            $(this).parent("li").removeClass("open").find(labeul).slideUp(300);
            if($(this).parent().find("ul").length>0){
                $(this).removeClass("active").find(".arrow").removeClass("open")
            }else{
                $(this).addClass("active")
            }
        }
    });

});
function selectALLAdmin() {
    var c= $("#account").val().charAt(0)
    if(c !=='3'){
        alert("您不具有此权限")
    }else {
        window.location.href="/thymeleaf/backAdminIndex/user"
    }
}


function addAdmin() {
    var c= $("#account").val().charAt(0)
    if(c !=='3'){
        alert("您不具有此权限")
    }else {
        window.location.href="/thymeleaf/backAdminAdd/html"
    }
}
