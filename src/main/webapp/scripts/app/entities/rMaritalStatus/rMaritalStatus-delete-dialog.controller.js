'use strict';

angular.module('aplikasiApp')
	.controller('RMaritalStatusDeleteController', function($scope, $uibModalInstance, entity, RMaritalStatus) {

        $scope.rMaritalStatus = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RMaritalStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
