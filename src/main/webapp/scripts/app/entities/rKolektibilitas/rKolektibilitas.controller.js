'use strict';

angular.module('aplikasiApp')
    .controller('RKolektibilitasController', function ($scope, $state, RKolektibilitas, RKolektibilitasSearch, ParseLinks) {

        $scope.rKolektibilitass = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            RKolektibilitas.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.rKolektibilitass = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            RKolektibilitasSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.rKolektibilitass = result;
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
            $scope.rKolektibilitas = {
                id_r_kolektibilitas: null,
                keterangan: null,
                id: null
            };
        };
    });
