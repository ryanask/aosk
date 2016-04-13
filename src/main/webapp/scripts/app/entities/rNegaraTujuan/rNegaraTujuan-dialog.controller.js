'use strict';

angular.module('aplikasiApp').controller('RNegaraTujuanDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RNegaraTujuan',
        function($scope, $stateParams, $uibModalInstance, entity, RNegaraTujuan) {

        $scope.rNegaraTujuan = entity;
        $scope.load = function(id) {
            RNegaraTujuan.get({id : id}, function(result) {
                $scope.rNegaraTujuan = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rNegaraTujuanUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rNegaraTujuan.id != null) {
                RNegaraTujuan.update($scope.rNegaraTujuan, onSaveSuccess, onSaveError);
            } else {
                RNegaraTujuan.save($scope.rNegaraTujuan, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
