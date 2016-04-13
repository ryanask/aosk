'use strict';

angular.module('aplikasiApp')
    .controller('RPendidikanDetailController', function ($scope, $rootScope, $stateParams, entity, RPendidikan) {
        $scope.rPendidikan = entity;
        $scope.load = function (id) {
            RPendidikan.get({id: id}, function(result) {
                $scope.rPendidikan = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rPendidikanUpdate', function(event, result) {
            $scope.rPendidikan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
