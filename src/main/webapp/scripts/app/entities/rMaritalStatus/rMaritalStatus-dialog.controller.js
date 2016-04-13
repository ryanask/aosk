'use strict';

angular.module('aplikasiApp').controller('RMaritalStatusDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RMaritalStatus',
        function($scope, $stateParams, $uibModalInstance, entity, RMaritalStatus) {

        $scope.rMaritalStatus = entity;
        $scope.load = function(id) {
            RMaritalStatus.get({id : id}, function(result) {
                $scope.rMaritalStatus = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rMaritalStatusUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rMaritalStatus.id != null) {
                RMaritalStatus.update($scope.rMaritalStatus, onSaveSuccess, onSaveError);
            } else {
                RMaritalStatus.save($scope.rMaritalStatus, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
