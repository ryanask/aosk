'use strict';

angular.module('aplikasiApp')
    .controller('RSektorKurController', function ($scope, $state, RSektorKur, RSektorKurSearch, ParseLinks) {

        $scope.rSektorKurs = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            RSektorKur.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.rSektorKurs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            RSektorKurSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.rSektorKurs = result;
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
            $scope.rSektorKur = {
                id_sektor: null,
                uraian: null,
                tanaman_keras: null,
                id: null
            };
        };
    });
