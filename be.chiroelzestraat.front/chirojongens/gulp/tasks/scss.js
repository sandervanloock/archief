var gulp = require('gulp');
var gutil = require('gulp-util');
var handleErrors = require('../util/handleErrors');
var config = require('../config').scss;
var sass = require( 'gulp-sass' );
var sourcemaps = require('gulp-sourcemaps');
var print = require('gulp-print');
var counter = 1;
gulp.task('scss', function () {
    return gulp.src(config.src)
        .pipe(sourcemaps.init())
        .pipe(sass({
            outputStyle: gutil.env.production ? 'compressed' : 'expanded',
            errLogToConsole: true,
            sourceComments : gutil.env.production ? false : true,
              onSuccess: function (css)
              {
                  console.log(  gutil.colors.yellow( '[' + counter++ + '] ' )  + gutil.colors.green( 'ok ')  );
              },
              onError: function ( err )
              {
                  console.log( gutil.colors.red( err ) );
              }
        }))
        .pipe(print())
        .pipe(sourcemaps.write())
        .on('error', handleErrors)
        .pipe(gulp.dest(config.dest));
});
