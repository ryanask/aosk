'use strict';

angular.module('aplikasiApp').controller('RStatusRekeningDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RStatusRekening',
        function($scope, $stateParams, $uibModalInstance, entity, RStatusRekening) {

        $scope.rStatusRekening = entity;
        $scope.load = function(id) {
            RStatusRekening.get({id : id}, function(result) {
                $scope.rStatusRekening = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rStatusRekeningUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rStatusRekening.id != null) {
                RStatusRekening.update($scope.rStatusRekening, onSaveSuccess, onSaveError);
            } else {
                RStatusRekening.save($scope.rStatusRekening, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
