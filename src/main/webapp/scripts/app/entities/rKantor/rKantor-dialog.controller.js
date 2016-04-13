'use strict';

angular.module('aplikasiApp').controller('RKantorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RKantor',
        function($scope, $stateParams, $uibModalInstance, entity, RKantor) {

        $scope.rKantor = entity;
        $scope.load = function(id) {
            RKantor.get({id : id}, function(result) {
                $scope.rKantor = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rKantorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rKantor.id != null) {
                RKantor.update($scope.rKantor, onSaveSuccess, onSaveError);
            } else {
                RKantor.save($scope.rKantor, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
