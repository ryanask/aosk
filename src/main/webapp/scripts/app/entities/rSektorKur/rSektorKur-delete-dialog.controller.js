'use strict';

angular.module('aplikasiApp')
	.controller('RSektorKurDeleteController', function($scope, $uibModalInstance, entity, RSektorKur) {

        $scope.rSektorKur = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RSektorKur.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
