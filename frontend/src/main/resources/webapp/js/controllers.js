'use strict';

var appControllers = angular.module('appControllers', []);


appControllers.controller('UserListController', [ '$scope', '$http',
    function($scope, $http) {
        $http.get('/users').success(function(response) {
            $scope.users = response;
        });
    }]);

appControllers.controller('UserDetailController', [ '$scope', '$routeParams', '$http',
    function($scope, $routeParams, $http) {
        $http.get('/users/' + $routeParams.username).success(function(response) {
            $scope.user = response;
        });
        $scope.user = {
            username: 'unknown'
        }
    }]);
