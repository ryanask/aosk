'use strict';

angular.module('aplikasiApp')
	.controller('RJenisAgunanDeleteController', function($scope, $uibModalInstance, entity, RJenisAgunan) {

        $scope.rJenisAgunan = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RJenisAgunan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
