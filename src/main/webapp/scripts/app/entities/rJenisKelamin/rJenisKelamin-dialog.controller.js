'use strict';

angular.module('aplikasiApp').controller('RJenisKelaminDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RJenisKelamin',
        function($scope, $stateParams, $uibModalInstance, entity, RJenisKelamin) {

        $scope.rJenisKelamin = entity;
        $scope.load = function(id) {
            RJenisKelamin.get({id : id}, function(result) {
                $scope.rJenisKelamin = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rJenisKelaminUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rJenisKelamin.id != null) {
                RJenisKelamin.update($scope.rJenisKelamin, onSaveSuccess, onSaveError);
            } else {
                RJenisKelamin.save($scope.rJenisKelamin, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
