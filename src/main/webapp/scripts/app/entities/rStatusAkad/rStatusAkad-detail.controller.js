'use strict';

angular.module('aplikasiApp')
    .controller('RStatusAkadDetailController', function ($scope, $rootScope, $stateParams, entity, RStatusAkad) {
        $scope.rStatusAkad = entity;
        $scope.load = function (id) {
            RStatusAkad.get({id: id}, function(result) {
                $scope.rStatusAkad = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rStatusAkadUpdate', function(event, result) {
            $scope.rStatusAkad = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
