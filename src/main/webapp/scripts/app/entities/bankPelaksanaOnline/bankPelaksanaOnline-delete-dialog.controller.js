'use strict';

angular.module('aplikasiApp')
	.controller('BankPelaksanaOnlineDeleteController', function($scope, $uibModalInstance, entity, BankPelaksanaOnline) {

        $scope.bankPelaksanaOnline = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            BankPelaksanaOnline.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
