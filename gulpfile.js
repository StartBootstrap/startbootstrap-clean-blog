var gulp = require('gulp');
var sass = require('gulp-sass');
var header = require('gulp-header');
var cleanCSS = require('gulp-clean-css');
var rename = require("gulp-rename");
var uglify = require('gulp-uglify');
var autoprefixer = require('gulp-autoprefixer');
var pkg = require('./package.json');
var browserSync = require('browser-sync').create();
var nunjucksRender = require('gulp-nunjucks-render');
var data = require('gulp-data');

// Copy third party libraries from /node_modules into /vendor
gulp.task('vendor', function() {
	// Font Awesome
	gulp.src([ './node_modules/@fortawesome/**/*', ]).pipe(gulp.dest('./vendor'))

	// jQuery
	gulp.src([ './node_modules/jquery/dist/*', '!./node_modules/jquery/dist/core.js' ]).pipe(gulp.dest('./vendor/jquery'))

});

// Compile SCSS
gulp.task('css:compile', function() {
	return gulp.src('./scss/**/*.scss').pipe(sass.sync({
		outputStyle : 'expanded'
	}).on('error', sass.logError)).pipe(autoprefixer({
		browsers : [ 'last 2 versions' ],
		cascade : false
	})).pipe(gulp.dest('./css'))
});

gulp.task('nunjucks', function() {
	// Gets .html and .nunjucks files in pages
	return gulp.src([ 'pages/*.html' ]).pipe(data(function() {
		return require('./data/fp-posts.json')
	}))
	// Renders template with nunjucks
	.pipe(nunjucksRender({
		path : [ 'templates' ]
	}))
	// output files in app folder
	.pipe(gulp.dest('./'))
});

// Minify CSS
gulp.task('css:minify', [ 'css:compile' ], function() {
	return gulp.src([ './css/*.css', '!./css/*.min.css' ]).pipe(cleanCSS()).pipe(rename({
		suffix : '.min'
	})).pipe(gulp.dest('./css')).pipe(browserSync.stream());
});

// CSS
gulp.task('css', [ 'css:compile', 'css:minify' ]);

// Minify JavaScript
gulp.task('js:minify', function() {
	return gulp.src([ './js/*.js', '!./js/*.min.js' ]).pipe(uglify()).pipe(rename({
		suffix : '.min'
	})).pipe(gulp.dest('./js')).pipe(browserSync.stream());
});

// JS
gulp.task('js', [ 'js:minify' ]);

// Default task
gulp.task('default', [ 'css', 'js', 'vendor' ]);

// Configure the browserSync task
gulp.task('browserSync', function() {
	browserSync.init({
		port: 3001,
		server : {
			baseDir : "./"
		}
	});
});

// Dev task
gulp.task('dev', [ 'css', 'js', 'nunjucks', 'browserSync' ], function() {
	gulp.watch('./scss/*.scss', [ 'css' ]);
	gulp.watch('./js/*.js', [ 'js' ]);
	gulp.watch('./*.html', browserSync.reload);
	gulp.watch('posts/*.html', browserSync.reload);
	gulp.watch('pages/*.html', [ 'nunjucks' ])
	gulp.watch('templates/*.html', [ 'nunjucks' ])
	gulp.watch('templates/partials/*.html', [ 'nunjucks' ])
});
