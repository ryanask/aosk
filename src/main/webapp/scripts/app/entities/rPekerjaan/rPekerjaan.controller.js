'use strict';

angular.module('aplikasiApp')
    .controller('RPekerjaanController', function ($scope, $state, RPekerjaan, RPekerjaanSearch, ParseLinks) {

        $scope.rPekerjaans = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            RPekerjaan.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.rPekerjaans = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            RPekerjaanSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.rPekerjaans = result;
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
            $scope.rPekerjaan = {
                id_r_pekerjaan: null,
                keterangan: null,
                id: null
            };
        };
    });
