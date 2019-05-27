// // function to find all the blog posts
// function search_post() {
// 	var glob = require("glob")

// 	// search all files ending with html and return the file name
// 	glob("posts/*.html", {realpath: false, nodir: true}, function (er, files) {
// 		// files is an array of filenames.
// 		// If the `nonull` option is set, and nothing
// 		// was found, then files is ["**/*.js"]
// 		// er is an error object or null.

// 		for (file of files){
// 			console.log(file) // filename with relative path
// 			file = file.substring(9,file.length-1) // file name, remove relative path
// 		// console.log(file)

// 		}
	
// 		console.log(files);
// 		console.log(files.length)

// 		return files.length;
		
// 	})
// }

// l = search_post(); 
// console.log(l);