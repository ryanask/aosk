'use strict';

angular.module('aplikasiApp')
    .controller('RPekerjaanDetailController', function ($scope, $rootScope, $stateParams, entity, RPekerjaan) {
        $scope.rPekerjaan = entity;
        $scope.load = function (id) {
            RPekerjaan.get({id: id}, function(result) {
                $scope.rPekerjaan = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rPekerjaanUpdate', function(event, result) {
            $scope.rPekerjaan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
