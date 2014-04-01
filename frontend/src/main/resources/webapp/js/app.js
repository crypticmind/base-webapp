
var app = angular.module('app', [
    'ngRoute',
    'appControllers'
]);

app.config([
   '$routeProvider',
    function($routeProvider) {
        $routeProvider
            .when('/users', {
                templateUrl: 'partials/user-list.html',
                controller: 'UserListController'
            })
            .when('/users/:username', {
                templateUrl: 'partials/user-detail.html',
                controller: 'UserDetailController'
            })
            .otherwise({
                redirectTo: '/users'
            });
    }
]);
