'use strict';

angular.module('aplikasiApp').controller('RPengikatanAgunanDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RPengikatanAgunan',
        function($scope, $stateParams, $uibModalInstance, entity, RPengikatanAgunan) {

        $scope.rPengikatanAgunan = entity;
        $scope.load = function(id) {
            RPengikatanAgunan.get({id : id}, function(result) {
                $scope.rPengikatanAgunan = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rPengikatanAgunanUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rPengikatanAgunan.id != null) {
                RPengikatanAgunan.update($scope.rPengikatanAgunan, onSaveSuccess, onSaveError);
            } else {
                RPengikatanAgunan.save($scope.rPengikatanAgunan, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
