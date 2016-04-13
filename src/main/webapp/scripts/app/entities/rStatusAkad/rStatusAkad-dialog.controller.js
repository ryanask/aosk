'use strict';

angular.module('aplikasiApp').controller('RStatusAkadDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RStatusAkad',
        function($scope, $stateParams, $uibModalInstance, entity, RStatusAkad) {

        $scope.rStatusAkad = entity;
        $scope.load = function(id) {
            RStatusAkad.get({id : id}, function(result) {
                $scope.rStatusAkad = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rStatusAkadUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rStatusAkad.id != null) {
                RStatusAkad.update($scope.rStatusAkad, onSaveSuccess, onSaveError);
            } else {
                RStatusAkad.save($scope.rStatusAkad, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
