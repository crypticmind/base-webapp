'use strict';

var appControllers = angular.module('appControllers', []);


appControllers.controller('UserListController', [ '$scope', '$http',
    function($scope, $http) {
        $http.get('users.json').success(function(response) {
            $scope.users = response.data;
        });
    }]);

appControllers.controller('UserDetailController', [ '$scope', '$routeParams', '$http',
    function($scope, $routeParams, $http) {
        $http.get('user_' + $routeParams.userId + '.json').success(function(response) {
            $scope.user = response.data;
        });
        $scope.user = {
            id: 'unknown',
            username: 'unknown'
        }
    }]);
