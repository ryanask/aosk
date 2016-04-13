'use strict';

angular.module('aplikasiApp')
    .controller('RNegaraTujuanDetailController', function ($scope, $rootScope, $stateParams, entity, RNegaraTujuan) {
        $scope.rNegaraTujuan = entity;
        $scope.load = function (id) {
            RNegaraTujuan.get({id: id}, function(result) {
                $scope.rNegaraTujuan = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:rNegaraTujuanUpdate', function(event, result) {
            $scope.rNegaraTujuan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
