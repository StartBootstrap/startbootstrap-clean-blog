var repeat = require('repeat-string');
var fs = require('fs');
var path = require('path');

var htmls = search_post();
console.log('\n');
console.log(htmls);

function search_post() {
	var files = fs.readdirSync("./posts/");
	var htmls = [];

	for(var i in files) {
		console.log(files[i]);
   		if(path.extname(files[i]) === ".html") {
    	   //do something
	       console.log("find one html");
	       htmls.push(files[i]);
   		}
   	}

   	return htmls;
}
