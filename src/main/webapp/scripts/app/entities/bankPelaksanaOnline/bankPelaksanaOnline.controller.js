'use strict';

angular.module('aplikasiApp')
    .controller('BankPelaksanaOnlineController', function ($scope, $state, BankPelaksanaOnline, BankPelaksanaOnlineSearch, ParseLinks) {

        $scope.bankPelaksanaOnlines = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            BankPelaksanaOnline.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.bankPelaksanaOnlines = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            BankPelaksanaOnlineSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.bankPelaksanaOnlines = result;
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
            $scope.bankPelaksanaOnline = {
                kode_bank: null,
                nama_bank: null,
                id_aktifitas_1: null,
                id_aktifitas_2: null,
                id: null
            };
        };
    });
