var root = "/";
var dest = root;
var src = root;

module.exports = {
    scss: {

        src:  'css/*.scss',
        watch: 'scss/**/*.scss',
        dest: 'css'
    },
    lint: {
        js: {
            src: src + "**/*.js",
            config: root + "/.jshintrc"
        },
        scss: {
            src: src + ['**/*.scss', '!node_modules/**/*.scss']
        }
    }
};
