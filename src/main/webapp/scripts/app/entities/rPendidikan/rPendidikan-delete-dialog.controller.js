'use strict';

angular.module('aplikasiApp')
	.controller('RPendidikanDeleteController', function($scope, $uibModalInstance, entity, RPendidikan) {

        $scope.rPendidikan = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RPendidikan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
