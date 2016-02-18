'use strict';

var gulp = require('gulp');
var sass = require('gulp-sass');
var bower = bower = require('gulp-bower');

var config = {
     bowerDir: './bower_components' ,
     cssPath: './css',
     sassPath: './sass/**/*.{sass,scss}'
}

// installs bootstrap to "/bower_components/"
gulp.task('bower', function() { 
    return bower()
         .pipe(gulp.dest(config.bowerDir)) 
});

// compiles css files into "/css/
gulp.task('sass', function () {
    return gulp.src(config.sassPath)
        .pipe(sass({
            // outputStyle: 'compressed',
            includePaths: [
                 config.bowerDir + '/bootstrap-sass/assets/stylesheets',
                config.sassPath
            ]
        })
        .on('error', sass.logError))
        .pipe(gulp.dest(config.cssPath));
});

// compiles sass files on change into "/css"
gulp.task('watch', function () {
    gulp.watch(config.sassPath, ['sass']);
});

// default command used to build your project
  gulp.task('build', ['bower', 'sass']);

// default commands to be excecuted when using the "gulp" command
  gulp.task('default', ['bower', 'sass', 'watch']);
