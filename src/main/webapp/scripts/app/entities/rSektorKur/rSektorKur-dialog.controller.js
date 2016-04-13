'use strict';

angular.module('aplikasiApp').controller('RSektorKurDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'RSektorKur',
        function($scope, $stateParams, $uibModalInstance, entity, RSektorKur) {

        $scope.rSektorKur = entity;
        $scope.load = function(id) {
            RSektorKur.get({id : id}, function(result) {
                $scope.rSektorKur = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:rSektorKurUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rSektorKur.id != null) {
                RSektorKur.update($scope.rSektorKur, onSaveSuccess, onSaveError);
            } else {
                RSektorKur.save($scope.rSektorKur, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
