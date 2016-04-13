'use strict';

angular.module('aplikasiApp')
    .controller('RStatusRekeningDetailController', function ($scope, $rootScope, $stateParams, entity, RStatusRekening) {
        $scope.rStatusRekening = entity;
        $scope.load = function (id) {
            RStatusRekening.get({id: id}, function(result) {
                $scope.rStatusRekening = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rStatusRekeningUpdate', function(event, result) {
            $scope.rStatusRekening = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
