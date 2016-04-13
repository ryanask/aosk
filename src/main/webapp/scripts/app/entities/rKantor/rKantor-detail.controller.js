'use strict';

angular.module('aplikasiApp')
    .controller('RKantorDetailController', function ($scope, $rootScope, $stateParams, entity, RKantor) {
        $scope.rKantor = entity;
        $scope.load = function (id) {
            RKantor.get({id: id}, function(result) {
                $scope.rKantor = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rKantorUpdate', function(event, result) {
            $scope.rKantor = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
