'use strict';

angular.module('aplikasiApp')
    .factory('BankPelaksanaOnlineSearch', function ($resource) {
        return $resource('api/_search/bankPelaksanaOnlines/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
