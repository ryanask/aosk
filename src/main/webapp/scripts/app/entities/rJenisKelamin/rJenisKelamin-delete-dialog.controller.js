'use strict';

angular.module('aplikasiApp')
	.controller('RJenisKelaminDeleteController', function($scope, $uibModalInstance, entity, RJenisKelamin) {

        $scope.rJenisKelamin = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RJenisKelamin.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
