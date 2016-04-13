'use strict';

angular.module('aplikasiApp')
    .controller('RSektorKurDetailController', function ($scope, $rootScope, $stateParams, entity, RSektorKur) {
        $scope.rSektorKur = entity;
        $scope.load = function (id) {
            RSektorKur.get({id: id}, function(result) {
                $scope.rSektorKur = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rSektorKurUpdate', function(event, result) {
            $scope.rSektorKur = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
