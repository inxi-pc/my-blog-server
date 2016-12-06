module.exports = function(grunt) {
    grunt.initConfig({
        env: {
            prod: grunt.file.readJSON('env.prod.json'),
            dev: grunt.file.readJSON('env.dev.json'),
            pathToConfig: './src/main/resources/',
            appConfigName: 'config.properties',
            dbConfigName: 'database.properties'
        }
    });

    grunt.registerTask('build', function (env) {
        grunt.config.requires('env.pathToConfig');
        grunt.config.requires('env.prod');
        grunt.config.requires('env.dev');
        var pathToConfig = grunt.config('env.pathToConfig');
        var envProd = grunt.config('env.prod');
        var envDev = grunt.config('env.dev');

        var appConfig = "";
        var dbConfig = "";
        if (env == 'prod') {
            appConfig = genProperties(envProd['app']);
            dbConfig = genProperties(envProd['db']);
        } else {
            appConfig = genProperties(envDev['app']);
            dbConfig = genProperties(envDev['db']);
        }
        grunt.file.write(pathToConfig + grunt.config('env.appConfigName') , appConfig);
        grunt.file.write(pathToConfig + grunt.config('env.dbConfigName'), dbConfig);
    })
};

function genProperties(json) {
    var properties = '';
    for (var k in json) {
        properties += k + '=' + json[k] + '\n';
    }

    return properties;
}

