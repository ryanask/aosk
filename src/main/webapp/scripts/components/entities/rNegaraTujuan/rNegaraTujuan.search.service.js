'use strict';

angular.module('aplikasiApp')
    .factory('RNegaraTujuanSearch', function ($resource) {
        return $resource('api/_search/rNegaraTujuans/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
