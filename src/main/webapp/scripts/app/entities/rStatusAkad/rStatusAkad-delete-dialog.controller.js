'use strict';

angular.module('aplikasiApp')
	.controller('RStatusAkadDeleteController', function($scope, $uibModalInstance, entity, RStatusAkad) {

        $scope.rStatusAkad = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RStatusAkad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
