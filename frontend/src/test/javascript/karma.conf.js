module.exports = function(config){
    config.set({

        basePath : '../../../',

        files : [
            'bower_components/angular/angular.js',
            'bower_components/angular-route/angular-route.js',
            'bower_components/angular-mocks/angular-mocks.js',
            /*
            'src/main/resources/webapp/lib/angular.min.js',
            'src/main/resources/webapp/lib/angular-route.min.js',
            */
            'src/main/resources/webapp/js/**/*.js',
            'src/test/javascript/unit/**/*.js'
        ],

        autoWatch : true,

        frameworks: ['jasmine'],

        browsers : ['Chrome'],

        plugins : [
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-jasmine'
        ],

        junitReporter : {
            outputFile: 'target/unit.xml',
            suite: 'unit'
        }

    });
};
