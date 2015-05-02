var gulp = require('gulp');
var gutil = require('gulp-util');

var text = gutil.env.production ? 'PRODUCTION' : 'DEVELOPMENT';
gutil.log( gutil.colors.red( "IN " + text + " MODE." ))
gulp.task('default', ['server', 'watch']);