'use strict';

angular.module('aplikasiApp')
    .controller('RJenisAgunanDetailController', function ($scope, $rootScope, $stateParams, entity, RJenisAgunan) {
        $scope.rJenisAgunan = entity;
        $scope.load = function (id) {
            RJenisAgunan.get({id: id}, function(result) {
                $scope.rJenisAgunan = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rJenisAgunanUpdate', function(event, result) {
            $scope.rJenisAgunan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
