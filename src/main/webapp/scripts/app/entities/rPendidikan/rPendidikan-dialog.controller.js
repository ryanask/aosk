'use strict';

angular.module('aplikasiApp').controller('RPendidikanDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RPendidikan',
        function($scope, $stateParams, $uibModalInstance, entity, RPendidikan) {

        $scope.rPendidikan = entity;
        $scope.load = function(id) {
            RPendidikan.get({id : id}, function(result) {
                $scope.rPendidikan = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rPendidikanUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rPendidikan.id != null) {
                RPendidikan.update($scope.rPendidikan, onSaveSuccess, onSaveError);
            } else {
                RPendidikan.save($scope.rPendidikan, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
