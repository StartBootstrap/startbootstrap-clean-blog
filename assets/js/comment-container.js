// Get a reference to the container
var comment-container = document.getElementById('comment-container');

document.addEventListener('keypress', function (event) {
    // Check if the key pressed is the 'c' key
    if (event.key === 'c') {
        // Toggle the visibility of the comment-container
        if (comment-container.style.display === 'none') {
            comment-container.style.display = 'block';
        } else {
            comment-container.style.display = 'none';
        }
    }
});