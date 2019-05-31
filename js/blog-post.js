// Author: Maxwell Li
// Date: 2019.05.28
// To load this file into html, run "watchify -t brfs js/blog-post.js -o js/blog-bundle.js"

var fs = require('fs');
var path = require('path');

// define some global variables
var blogPerPage = 3;
var currPage = 1;
var numPage;
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
var MODE = {
    INDEX: 0,
    TAG: 1,
    SEARCH: 2
}
var blogs = [];
var keyword;
var mode;
var error;
var defaultImage = './posts/default.jpg';

// jQuery main execution function
$(function(){
	init_page();
});

/**************************************** homepage logic ****************************************************/
// function to determine how many pages we need for blogs based on the number of blogs
function init_page(){
	// find the keyword we received
	// by default, index page doesn't have a keyword (mode undefined)
	// if we have a keyword, then show only blogs related to keyword and hide the paginator (mode = tag or search)
	var searchParam = window.location.search;
	if (searchParam.includes("tag")){
		console.log("tag mode")
		keyword = searchParam.substring(searchParam.indexOf("=")+1,searchParam.length);
		mode = MODE.TAG;
	}
	else if (searchParam.includes("keyword")){
		console.log("search mode")
		keyword = searchParam.substring(searchParam.indexOf("=")+1,searchParam.length);
		mode = MODE.SEARCH;
	}
	else{
		console.log(mode)
		mode = MODE.INDEX;
	}
	console.log("tag is " + keyword);

	// initialize error message flag to be true
	error = true;

	// find all blogs
	var blogs = search_blog();
	blogs = blogs.reverse(); // by default, unix system will already sort the posts in natural order

	// find number of pages needed
	numPage = Math.ceil(blogs.length/blogPerPage);

	// if only one page needed, disable paginator
	// if in tag/search mode, disable paginator
	if (numPage >0 && mode == MODE.INDEX){
		setup_pagination();
	}

	setup_page();
}

// function to find all the blog posts
function search_blog() {
	var files = fs.readdirSync("./posts/");

	for(var i in files) {
   		if(path.extname(files[i]) === ".html") {
   			blogs.push(files[i]);
   		}
   	}

   	return blogs;
}

// function to setup page
function setup_page(){
	// zero-index currPage
	var index = (currPage-1)*blogPerPage;

	// make sure index in range
	if (index < 0 || index >= blogs.length){
		console.log("error!");
	}
	else{
		// find the interation length based on if we are in tag/search mode
		var iterationLength;
		if (mode == MODE.INDEX) { // we are in index page, show blogPerPage blogs
			iterationLength = Math.min(index+blogPerPage,blogs.length);
			console.log("index");
		}
		else{ // we are in the other two modes, show all blogs containing the same keyword, therefore we iterating all of the blogs
			iterationLength = blogs.length;
		}
		// load up to blogPerPage blogs (not if the maximum number of blogs have reached)
		for (i = index; i < iterationLength; i++){
			parse_html("./posts/"+blogs[i],i);
		}
	}
}

function setup_pagination(){
	$("#paginator").html('<div id="paginator-placeholder"></div>');
	// disable newer when no more newer blogs
	if (currPage == 1){
		$("#paginator-placeholder").before('<li class="page-item disabled" id="newer_button"><a class="page-link" href="javascript:on_newer_blog()">&larr; Newer</a></li>');
	}
	else{
		$("#paginator-placeholder").before('<li class="page-item" id="newer_button"><a class="page-link" href="javascript:on_newer_blog()">&larr; Newer</a></li>');
	}

	// page number
	for (i = 1; i<= numPage; i++){
		// make current page active
		if (currPage == i){
			$("#paginator-placeholder").before('<li class="page-item active"><a class="page-link" href="javascript:on_page_number('+i+')">'+i+'</a></li>');
		}
		else{
			$("#paginator-placeholder").before('<li class="page-item"><a class="page-link" href="javascript:on_page_number('+i+')">'+i+'</a></li>');
		}

	}

	// disable older when no more older blogs
	if (currPage == numPage){
		$("#paginator-placeholder").before('<li class="page-item disabled" id="older_button"><a class="page-link" href="javascript:on_older_blog()">Older &rarr;</a></li>');
	}
	else{
		$("#paginator-placeholder").before('<li class="page-item" id="older_button"><a class="page-link" href="javascript:on_older_blog()">Older &rarr;</a></li>');
	}
}


/**************************************** blog logic ****************************************************/
// parse info of html
function parse_html(file,index){
	// initialize the reader
	var xhr = new XMLHttpRequest();

	// add event listener
    xhr.addEventListener("load", function() {
    	load_card(xhr.response,file,index); // callback to load_card
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

	if (mode == MODE.INDEX || (mode == MODE.TAG && htmlDoc.getElementById("tag").childNodes[0].innerText == keyword) || (mode == MODE.SEARCH && htmlDoc.body.textContent.match(RegExp(keyword,"gi"))) ){ // we are in index page or keyword matches, show
		if (error){ // clear error message
			error = false;
			document.getElementById('card-list-placeholder').innerText = '';
		}
		var image = file.replace("html","jpg");
		var date = file.slice(8,18);
		date = date.split("_");

		// add card object to card list
		document.getElementById('card-list-placeholder').innerHTML += '<div class="card mb-4" id="post_'+i.toString()+'"></div>';
		// add image
		document.getElementById('post_'+i.toString()).innerHTML += '<img class="card-img-top" src="'+
																	image+
																	'" alt="Card image cap"/>'; //http://placehold.it/750x300
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
		// add date and tag
		var tagString = "'"+htmlDoc.getElementById("tag").childNodes[0].innerText+"'"
		document.getElementById('post_'+i.toString()).innerHTML += '<div class="card-footer text-muted">Posted on '+month.get(date[1])+" "+date[2]+", "+date[0]+
																	'  <kbd id="tag"><a href="javascript:on_tag('+tagString+')">'+
																	htmlDoc.getElementById("tag").childNodes[0].innerText+'</a></kbd>';

		// in case an image doesnt exist, use default
		$(".card-img-top").on("error", function(){
	        $(this).attr('src', './posts/default.jpg');
	    });
	}
	else{
		if (error){ // show error message
			document.getElementById('card-list-placeholder').innerText = 'There is no blog :(';
		}
	}
}

/**************************************** pagination logic ****************************************************/
// function for older blog button
window.on_older_blog = function (){
	currPage ++;

	// check if page exceeds maximum
	if (currPage > numPage){
		currPage = numPage; // prevent overflow
		document.getElementById("older_button").disabled = true;
	}

	// setup page and pagination based on current page
	document.getElementById('blog-title').scrollIntoView(true);
	document.getElementById('card-list-placeholder').innerHTML = '';
	setup_page();
	setup_pagination();
}

// function for newer blog button
window.on_newer_blog = function (){
	currPage --;

	// check if page exceeds minimum
	if (currPage > 1){
		currPage = numPage; // prevent overflow
	}

	// setup page and pagination based on current page
	document.getElementById('blog-title').scrollIntoView(true);
	document.getElementById('card-list-placeholder').innerHTML = '';
	setup_page();
	setup_pagination();
}

window.on_page_number = function(pageNum){
	currPage = pageNum;

	// setup page and pagination based on current page
	document.getElementById('blog-title').scrollIntoView(true);
	document.getElementById('card-list-placeholder').innerHTML = '';
	setup_page();
	setup_pagination();
}

/**************************************** tage logic  ****************************************************/
window.on_tag = function(tag){
	window.location.href = "./index.html?tag="+tag;
}

/**************************************** search logic  ****************************************************/
window.on_search = function(keyword){
	window.location.href = "./index.html?keyword="+keyword;
}

window.on_keyup = function(event){
	if (event.keyCode == 13){ // enter has received
		document.getElementsByName("search-btn")[0].click();
	}
}
/**************************************** test  ****************************************************/
// dummy test function to test adding elemetns to html
function dummy(){
	console.log("dummy");
}