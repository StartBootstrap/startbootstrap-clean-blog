if (window.matchMedia("(max-width: 768px)").matches) {
    // Get a reference to the container
    var container = document.getElementById('container');

    // Add touch event listeners to the document
    document.addEventListener('touchstart', handleTouchStart, false);
    document.addEventListener('touchmove', handleTouchMove, false);

    // Variables to track swipe gesture
    var xDown = null;
    var yDown = null;

    // Handle touch start event
    function handleTouchStart(event) {
        xDown = event.touches[0].clientX;
        yDown = event.touches[0].clientY;
    }

    // Handle touch move event
    function handleTouchMove(event) {
        if (!xDown || !yDown) {
            return;
        }

        var xUp = event.touches[0].clientX;
        var yUp = event.touches[0].clientY;

        var xDiff = xDown - xUp;
        var yDiff = yDown - yUp;

        // Check if swipe is horizontal and long enough
        if (Math.abs(xDiff) > Math.abs(yDiff) && Math.abs(xDiff) > 100) {
            // Swipe left to show the container
            if (xDiff > 0 && container.style.display === 'none') {
                container.style.display = 'block';
                container.classList.add('show');
            }
            // Swipe right to hide the container
            else if (xDiff < 0 && container.style.display === 'block') {
                container.classList.remove('show');
                setTimeout(function () {
                    container.style.display = 'none';
                }, 300);
            }
        }

        // Reset variables for next swipe gesture
        xDown = null;
        yDown = null;
    }

} else {
    document.addEventListener('keypress', function (event) {
        // Check if the key pressed is the 'c' key
        if (event.key === 'c') {
            // Toggle the visibility of the comment-container
            if (container.style.display === 'none') {
                container.style.display = 'block';
            } else {
                container.style.display = 'none';
            }
        }
    });
}