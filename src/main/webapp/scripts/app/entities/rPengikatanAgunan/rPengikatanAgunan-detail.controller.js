'use strict';

angular.module('aplikasiApp')
    .controller('RPengikatanAgunanDetailController', function ($scope, $rootScope, $stateParams, entity, RPengikatanAgunan) {
        $scope.rPengikatanAgunan = entity;
        $scope.load = function (id) {
            RPengikatanAgunan.get({id: id}, function(result) {
                $scope.rPengikatanAgunan = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rPengikatanAgunanUpdate', function(event, result) {
            $scope.rPengikatanAgunan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
