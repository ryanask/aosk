'use strict';

angular.module('aplikasiApp')
	.controller('RKantorDeleteController', function($scope, $uibModalInstance, entity, RKantor) {

        $scope.rKantor = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RKantor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
