$(document).ready(function () {

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

})