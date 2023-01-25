/*
* Use this example from your website in Google Drive called "Riley's Website" to make your blog's menu through JavaScript.
* This will prevent you from having to change the menu in every single webpage.
* Consider an additional piece of JavaScript to create the desired JavaScript files in each webpage.
*/
document.getElementById("foot01").innerHTML =
"<p>&copy;  " + new Date().getFullYear() + " Riley Grigg</p>";
document.getElementById("nav01").innerHTML =
"<ul id='menu'>" +
"<li><a href='home.html'>Home: About</a></li>" +
"<li><a href='http://google.com'>Google</a></li>"
"</ul>";
