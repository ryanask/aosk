'use strict';

angular.module('aplikasiApp')
    .controller('RMaritalStatusController', function ($scope, $state, RMaritalStatus, RMaritalStatusSearch, ParseLinks) {

        $scope.rMaritalStatuss = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            RMaritalStatus.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.rMaritalStatuss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            RMaritalStatusSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.rMaritalStatuss = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.rMaritalStatus = {
                id_r_marital_status: null,
                keterangan: null,
                id: null
            };
        };
    });
