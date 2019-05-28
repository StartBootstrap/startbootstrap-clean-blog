// Author: Maxwell Li
// Date: 2019.05.28
// To load this file into html, run "watchify -t brfs js/blog-post.js -o js/blog-bundle.js"

var fs = require('fs');
var path = require('path');

search_post();

var blog = function(title, date, paragraphs, image){
	this.title = title;
	this.date = date;

}

var month = new Map([
	["01", "Januray"],
	["02", "Feburary"],
	["03", "March"],
	["04", "April"],
	["05", "May"],
	["06", "June"],
	["07", "July"],
	["08", "August"],
	["09", "September"],
	["10", "October"],
	["11", "November"],
	["12", "December"]
]);

// function to find all the blog posts
function search_post() {
	document.getElementById('card-list-placeholder').innerHTML = '';

	var files = fs.readdirSync("./posts/");

	for(var i in files) {
   		if(path.extname(files[i]) === ".html") {
	       parse_html("./posts/"+files[i],i);
   		}
   	}
}

// parse info about page
function parse_html(file,index){
	// initialize the reader
	var xhr = new XMLHttpRequest();

	// add event listener
    xhr.addEventListener("load", function() {
    	load_card(xhr.response,file,index); // callback to load_card
    	console.log(index);
    }, false);

    // open file and send even
    xhr.open('GET', file);
    xhr.send();
}

// function to load cards from post htmls
function load_card(content,file,i) {
	// read content as html
	parser = new DOMParser();
	htmlDoc = parser.parseFromString(content,"text/html");

	console.log(file)
	var image = file.replace("html","jpg");
	var date = file.slice(8,18);
	date = date.split("_");

	// add card object to card list
	document.getElementById('card-list-placeholder').innerHTML += '<div class="card mb-4" id="post_'+i.toString()+'"></div>';
	// add image
	document.getElementById('post_'+i.toString()).innerHTML += '<img class="card-img-top" src="'+
																image+
																'" alt="Card image cap">'; //http://placehold.it/750x300
	// add title
	document.getElementById('post_'+i.toString()).innerHTML += '<div class="card-body">'+
															   	'<h2 class="card-title" align-items-center>'+
																htmlDoc.getElementById("title").childNodes[0].nodeValue+
																'</h2>';
	// // add subheading
	// document.getElementById('post_'+i.toString()).innerHTML += '<p class="card-text">'+
	// 															htmlDoc.getElementById("subheading").childNodes[0].nodeValue+
	// 															'</p>';
	// add hyperlink
	document.getElementById('post_'+i.toString()).innerHTML += '<a href="'+
																file+
																'" class="btn btn-primary">Read More &rarr;</a>'+
																'</div>';
	// add date
	document.getElementById('post_'+i.toString()).innerHTML += '<div class="card-footer text-muted">Posted on '+month.get(date[1])+" "+date[2]+", "+date[0]+'<a href="#"> &lt TAG &gt </a></div>';
}

// dummy test function to test adding elemetns to html
function dummy(){
	document.getElementById('loading-txt').innerHTML = '';
	i = 123;
	document.getElementById('loading-txt').innerHTML += '<h5 class="title" id="card-placeholder">'+i.toString()+'</h4>';
}