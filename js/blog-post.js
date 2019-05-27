var blog = function(title, date, paragraphs, image){
	this.title = title;
	this.date = date;
	
}

// function to load cards from post htmls
function load_card() {
	// files = search_post();

	// reset card-list-placeholder
	document.getElementById('card-list-placeholder').innerHTML = '';

	for (i = 0; i < 5; i ++){

		// add card object to card list
		document.getElementById('card-list-placeholder').innerHTML += '<div class="card mb-4" id="post_'+i.toString()+'"></div>';
		// add image
		document.getElementById('post_'+i.toString()).innerHTML += '<img class="card-img-top" src='+ '"img/about-bg.jpg"' + 'alt="Card image cap">'; //http://placehold.it/750x300
		// add content
		document.getElementById('post_'+i.toString()).innerHTML += '<div class="card-body"><h2 class="card-title">Post Title</h2><p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis aliquid atque, nulla? Quos cum ex quis soluta, a laboriosam. Dicta expedita corporis animi vero voluptate voluptatibus possimus, veniam magni quis!</p><a href="posts/2019_05_21_sample_post.html" class="btn btn-primary">Read More &rarr;</a></div>';
		// add date
		document.getElementById('post_'+i.toString()).innerHTML += '<div class="card-footer text-muted">Posted on May 21st, 2019<a href="#"> &lt TAG &gt </a></div>';
	}
}

// function to find all the blog posts
function search_post() {
	var glob = require("glob")

	// search all files ending with html and return the file name
	glob("posts/*.html", {realpath: false, nodir: true}, function (er, files) {
		// files is an array of filenames.
		// If the `nonull` option is set, and nothing
		// was found, then files is ["**/*.js"]
		// er is an error object or null.

		return files;
	})
}


// parse info about page
function parse_html() {
	// parse infomation of title and introduction text
}


// dummy test function to test adding elemetns to html
function dummy(){
	document.getElementById('loading-txt').innerHTML = '';
	i = 123;
	document.getElementById('loading-txt').innerHTML += '<h5 class="title" id="card-placeholder">'+i.toString()+'</h4>';
}


// $(function() {
	
	// console.log(files.length) // number of posts found
	// 	for (file of files){
	// 		// console.log(file) // filename with relative path
	// 		file = file.substring(9,file.length-1) // file name, remove relative path
	// 		// console.log(file)

	// 		var card = document.createElement("card mb-4");
	// 		document.body.appendChild(card);
	// 	}
// });