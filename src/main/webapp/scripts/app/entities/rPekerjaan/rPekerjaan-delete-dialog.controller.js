'use strict';

angular.module('aplikasiApp')
	.controller('RPekerjaanDeleteController', function($scope, $uibModalInstance, entity, RPekerjaan) {

        $scope.rPekerjaan = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RPekerjaan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
