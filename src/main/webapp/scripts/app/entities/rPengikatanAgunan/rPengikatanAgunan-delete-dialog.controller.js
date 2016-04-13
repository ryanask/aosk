'use strict';

angular.module('aplikasiApp')
	.controller('RPengikatanAgunanDeleteController', function($scope, $uibModalInstance, entity, RPengikatanAgunan) {

        $scope.rPengikatanAgunan = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RPengikatanAgunan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
