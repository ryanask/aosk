'use strict';

angular.module('aplikasiApp')
    .controller('RKolektibilitasDetailController', function ($scope, $rootScope, $stateParams, entity, RKolektibilitas) {
        $scope.rKolektibilitas = entity;
        $scope.load = function (id) {
            RKolektibilitas.get({id: id}, function(result) {
                $scope.rKolektibilitas = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rKolektibilitasUpdate', function(event, result) {
            $scope.rKolektibilitas = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
