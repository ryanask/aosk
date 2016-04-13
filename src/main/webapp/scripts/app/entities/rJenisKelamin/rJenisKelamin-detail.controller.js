'use strict';

angular.module('aplikasiApp')
    .controller('RJenisKelaminDetailController', function ($scope, $rootScope, $stateParams, entity, RJenisKelamin) {
        $scope.rJenisKelamin = entity;
        $scope.load = function (id) {
            RJenisKelamin.get({id: id}, function(result) {
                $scope.rJenisKelamin = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rJenisKelaminUpdate', function(event, result) {
            $scope.rJenisKelamin = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
