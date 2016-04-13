'use strict';

angular.module('aplikasiApp')
    .factory('RPengikatanAgunanSearch', function ($resource) {
        return $resource('api/_search/rPengikatanAgunans/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
