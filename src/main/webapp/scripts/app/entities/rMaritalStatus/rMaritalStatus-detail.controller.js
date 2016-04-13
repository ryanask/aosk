'use strict';

angular.module('aplikasiApp')
    .controller('RMaritalStatusDetailController', function ($scope, $rootScope, $stateParams, entity, RMaritalStatus) {
        $scope.rMaritalStatus = entity;
        $scope.load = function (id) {
            RMaritalStatus.get({id: id}, function(result) {
                $scope.rMaritalStatus = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rMaritalStatusUpdate', function(event, result) {
            $scope.rMaritalStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
