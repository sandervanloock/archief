var gulp = require('gulp');
var config = require('../config');
var path = require('path');
var gutil = require('gulp-util');

gulp.task('watch', function () {
    // scss
    var scssWatcher = gulp.watch(config.scss.watch, ['scss']);
    scssWatcher.on('change', function (event) {
        gutil.log( gutil.colors.yellow( 'Scss watcher says ' + path.basename(event.path) ) + ' is changed.' );
    });

});
