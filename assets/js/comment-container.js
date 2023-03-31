document.addEventListener('keypress', function (event) {
    // Check if the key pressed is the 'c' key
    if (event.key === 'c') {
        // Show the comments section by changing the comment-container's display property
        document.getElementById('comment-container').style.display = 'block';
    }
});