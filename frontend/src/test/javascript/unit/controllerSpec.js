'use strict';

describe('controllers', function() {

    beforeEach(module('appControllers'));

    var scope;

    beforeEach(inject(function($rootScope) {
        scope = $rootScope.$new();
    }));

    it('should expose a UserListController', inject(function($controller) {
        var userListController = $controller('UserListController', { $scope: scope });
        expect(userListController).toBeDefined();
    }));

    it('should expose a UserDetailController', inject(function($controller) {
        var userListController = $controller('UserListController', { $scope: scope });
        expect(userListController).toBeDefined();
    }));

});
