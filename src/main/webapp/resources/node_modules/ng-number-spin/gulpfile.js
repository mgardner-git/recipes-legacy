var gulp = require('gulp');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var minifyCSS = require('gulp-minify-css');

gulp.task('build:js', function () {

    var pathToJSApp =
        ['src/js/ng-number-spin.js'];

    return gulp.src(pathToJSApp)
        .pipe(gulp.dest('dist/js/'))
        .pipe(uglify())
        .pipe(rename({basename: 'ng-number-spin', suffix: '.min', extname: '.js'}))
        .on('error', function (error) {
            console.error('\nError on JS minification: \n', error.toString());
            this.emit('end');
        })
        .pipe(gulp.dest('dist/js/'));
});

gulp.task('build:css', function () {

    var pathToCSS = [
        'src/css/ng-number-spin.css'
    ];

    return gulp.src(pathToCSS)
        .pipe(gulp.dest('dist/css/'))
        .pipe(minifyCSS({keepBreaks: false, keepSpecialComments: false}))
        .pipe(rename({basename: 'ng-number-spin', suffix: '.min', extname: '.css'}))
        .pipe(gulp.dest('dist/css/'))
});


gulp.task('build', ['build:js', 'build:css']);

gulp.task('default', ['build']);
