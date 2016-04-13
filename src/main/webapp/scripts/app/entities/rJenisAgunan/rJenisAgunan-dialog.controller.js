'use strict';

angular.module('aplikasiApp').controller('RJenisAgunanDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RJenisAgunan',
        function($scope, $stateParams, $uibModalInstance, entity, RJenisAgunan) {

        $scope.rJenisAgunan = entity;
        $scope.load = function(id) {
            RJenisAgunan.get({id : id}, function(result) {
                $scope.rJenisAgunan = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rJenisAgunanUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rJenisAgunan.id != null) {
                RJenisAgunan.update($scope.rJenisAgunan, onSaveSuccess, onSaveError);
            } else {
                RJenisAgunan.save($scope.rJenisAgunan, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
