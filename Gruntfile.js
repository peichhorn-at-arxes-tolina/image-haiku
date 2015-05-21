module.exports = function (grunt) {
    grunt.initConfig({

        sync: {
            main: {
                files: [{
                    cwd: 'src/main/resources/assets',
                    src: ['**'],
                    dest: 'build/resources/main/assets'
                }]
            }
        },
        watch: {
           files: 'src/main/resources/assets/**',
           tasks: ['sync']
       }
    });

    grunt.loadNpmTasks('grunt-sync');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.registerTask('default', ['sync', 'watch']);
};