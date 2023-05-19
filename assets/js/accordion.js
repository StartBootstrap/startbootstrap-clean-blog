var acc = document.getElementsByClassName("accordion");
var i;

for (i = 0; i < acc.length; i++) {
    acc[i].addEventListener("click", function () {
        this.classList.toggle("active");
        var panel = this.nextElementSibling;
        if (panel.style.display === "block") {
            panel.style.display = "none";
        } else {
            panel.style.display = "block";
        }
    });
}

$(document).ready(function () {
    // Code to open accordion when jumping to a tag
    var hash = window.location.hash;
    if (hash) {
        var element = $(hash);
        if (element.length) {
            element.trigger('click');
        }
    }
});
