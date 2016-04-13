'use strict';

angular.module('aplikasiApp')
    .controller('RPengikatanAgunanController', function ($scope, $state, RPengikatanAgunan, RPengikatanAgunanSearch, ParseLinks) {

        $scope.rPengikatanAgunans = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            RPengikatanAgunan.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.rPengikatanAgunans = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            RPengikatanAgunanSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.rPengikatanAgunans = result;
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
            $scope.rPengikatanAgunan = {
                id_r_pengikatan_agunan: null,
                uraian: null,
                id: null
            };
        };
    });
