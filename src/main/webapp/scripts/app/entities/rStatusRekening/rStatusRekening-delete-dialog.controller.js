'use strict';

angular.module('aplikasiApp')
	.controller('RStatusRekeningDeleteController', function($scope, $uibModalInstance, entity, RStatusRekening) {

        $scope.rStatusRekening = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RStatusRekening.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
