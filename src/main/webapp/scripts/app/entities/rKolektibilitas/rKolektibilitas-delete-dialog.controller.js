'use strict';

angular.module('aplikasiApp')
	.controller('RKolektibilitasDeleteController', function($scope, $uibModalInstance, entity, RKolektibilitas) {

        $scope.rKolektibilitas = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RKolektibilitas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
