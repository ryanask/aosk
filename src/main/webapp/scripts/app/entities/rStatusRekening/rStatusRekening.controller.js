'use strict';

angular.module('aplikasiApp')
    .controller('RStatusRekeningController', function ($scope, $state, RStatusRekening, RStatusRekeningSearch, ParseLinks) {

        $scope.rStatusRekenings = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            RStatusRekening.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.rStatusRekenings = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            RStatusRekeningSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.rStatusRekenings = result;
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
            $scope.rStatusRekening = {
                id_r_status_rekening: null,
                keterangan: null,
                id: null
            };
        };
    });
