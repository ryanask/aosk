'use strict';

angular.module('aplikasiApp').controller('RPekerjaanDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RPekerjaan',
        function($scope, $stateParams, $uibModalInstance, entity, RPekerjaan) {

        $scope.rPekerjaan = entity;
        $scope.load = function(id) {
            RPekerjaan.get({id : id}, function(result) {
                $scope.rPekerjaan = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rPekerjaanUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rPekerjaan.id != null) {
                RPekerjaan.update($scope.rPekerjaan, onSaveSuccess, onSaveError);
            } else {
                RPekerjaan.save($scope.rPekerjaan, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
