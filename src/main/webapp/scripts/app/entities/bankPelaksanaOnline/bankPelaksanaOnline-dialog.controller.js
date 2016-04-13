'use strict';

angular.module('aplikasiApp').controller('BankPelaksanaOnlineDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'BankPelaksanaOnline',
        function($scope, $stateParams, $uibModalInstance, entity, BankPelaksanaOnline) {

        $scope.bankPelaksanaOnline = entity;
        $scope.load = function(id) {
            BankPelaksanaOnline.get({id : id}, function(result) {
                $scope.bankPelaksanaOnline = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:bankPelaksanaOnlineUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bankPelaksanaOnline.id != null) {
                BankPelaksanaOnline.update($scope.bankPelaksanaOnline, onSaveSuccess, onSaveError);
            } else {
                BankPelaksanaOnline.save($scope.bankPelaksanaOnline, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
