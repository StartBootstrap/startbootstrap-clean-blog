let keysPressed = [];
document.addEventListener('keydown', (event) => {
    keysPressed.push(event.key);
    // If we type /d on our site, we will be directed to a Google Drive folder which contains all of the post drafts for this site. 
    if (keysPressed.join('') === '/d') {
        window.location.href = 'https://drive.google.com/drive/folders/1oqjvs-j_NYvvwWFoFz0dWvs1XgQr_Dv8';
    }
    // If we type /c on our site, we will be directed to a URL which will create a new Google Docs file in a folder with post drafts for this site.
    if (keysPressed.join('') === '/c') {
        window.location.href = 'https://docs.google.com/document/create?usp=drive_web&folder=1oqjvs-j_NYvvwWFoFz0dWvs1XgQr_Dv8';
    }
    /* If the escape key is pressed, clear the array.
    * This allows us to clear what we were typing if we made a mistake.
    */
    if (event.key === 'Escape') {
        keysPressed = [];
    }
});