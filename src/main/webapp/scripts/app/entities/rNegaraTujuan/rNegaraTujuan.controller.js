'use strict';

angular.module('aplikasiApp')
    .controller('RNegaraTujuanController', function ($scope, $state, RNegaraTujuan, RNegaraTujuanSearch, ParseLinks) {

        $scope.rNegaraTujuans = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            RNegaraTujuan.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.rNegaraTujuans = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            RNegaraTujuanSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.rNegaraTujuans = result;
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
            $scope.rNegaraTujuan = {
                id_r_negara_tujuan: null,
                keterangan: null,
                id: null
            };
        };
    });
