'use strict';

angular.module('aplikasiApp')
	.controller('RNegaraTujuanDeleteController', function($scope, $uibModalInstance, entity, RNegaraTujuan) {

        $scope.rNegaraTujuan = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RNegaraTujuan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
