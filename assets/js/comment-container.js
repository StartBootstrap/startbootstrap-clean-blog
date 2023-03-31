document.addEventListener('keypress', function (event) {
    // Check if the key pressed is the 'c' key
    if (event.key === 'c') {
        // Toggle the visibility of the accordion
        if (accordion.style.display === 'none') {
            accordion.style.display = 'block';
        } else {
            accordion.style.display = 'none';
        }
    }
});