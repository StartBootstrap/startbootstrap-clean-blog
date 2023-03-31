if (window.matchMedia("(max-width: 768px)").matches) {
    const container = document.getElementById('container');
    let startY = 0;
    let endY = 0;

    window.addEventListener('touchstart', (event) => {
        startY = event.touches[0].clientY;
    });

    window.addEventListener('touchend', (event) => {
        endY = event.changedTouches[0].clientY;
        const distance = startY - endY;

        if (distance > 50) {
            container.style.display = 'block';
        } else if (distance < -50) {
            container.style.display = 'none';
        }
    });

} else {
    document.addEventListener('keypress', function (event) {
        // Check if the key pressed is the 'c' key
        if (event.key === 'c') {
            // Toggle the visibility of the comment-container
            var container = document.getElementById('container');
            if (container.style.display === 'none') {
                container.style.display = 'block';
            } else {
                container.style.display = 'none';
            }
        }
    });
}
