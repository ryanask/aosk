'use strict';

angular.module('aplikasiApp')
    .controller('RJenisKelaminController', function ($scope, $state, RJenisKelamin, RJenisKelaminSearch, ParseLinks) {

        $scope.rJenisKelamins = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            RJenisKelamin.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.rJenisKelamins = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            RJenisKelaminSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.rJenisKelamins = result;
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
            $scope.rJenisKelamin = {
                id_r_jenis_kelamin: null,
                keterangan: null,
                id: null
            };
        };
    });
