'use strict';

angular.module('aplikasiApp').controller('RKolektibilitasDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RKolektibilitas',
        function($scope, $stateParams, $uibModalInstance, entity, RKolektibilitas) {

        $scope.rKolektibilitas = entity;
        $scope.load = function(id) {
            RKolektibilitas.get({id : id}, function(result) {
                $scope.rKolektibilitas = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rKolektibilitasUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rKolektibilitas.id != null) {
                RKolektibilitas.update($scope.rKolektibilitas, onSaveSuccess, onSaveError);
            } else {
                RKolektibilitas.save($scope.rKolektibilitas, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
