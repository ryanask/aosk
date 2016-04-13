'use strict';

angular.module('aplikasiApp')
    .controller('RKantorController', function ($scope, $state, RKantor, RKantorSearch, ParseLinks) {

        $scope.rKantors = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            RKantor.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.rKantors = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            RKantorSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.rKantors = result;
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
            $scope.rKantor = {
                id_kantor: null,
                kantor: null,
                alamat: null,
                telepon: null,
                id: null
            };
        };
    });
