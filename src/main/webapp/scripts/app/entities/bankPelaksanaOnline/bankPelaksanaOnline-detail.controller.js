'use strict';

angular.module('aplikasiApp')
    .controller('BankPelaksanaOnlineDetailController', function ($scope, $rootScope, $stateParams, entity, BankPelaksanaOnline) {
        $scope.bankPelaksanaOnline = entity;
        $scope.load = function (id) {
            BankPelaksanaOnline.get({id: id}, function(result) {
                $scope.bankPelaksanaOnline = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:bankPelaksanaOnlineUpdate', function(event, result) {
            $scope.bankPelaksanaOnline = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
