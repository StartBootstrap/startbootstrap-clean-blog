var latestPosition = 0;

$(window).scroll(function () {
    var $extra = $('#extra');
    if ($(window).scrollTop() + $(window).height() == $(document).height()) {
        if ($extra.is(':visible')) {
            alert('time to reload');
        } else {
            setTimeout(function () {
                $extra.show();
            }, 300)
        }
    }
    // console.log($(window).scrollTop() + " " + latestPosition)
    if ($(window).scrollTop() < latestPosition)
        $extra.hide();
    latestPosition = $(window).scrollTop();
})