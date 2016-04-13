'use strict';

angular.module('aplikasiApp')
    .factory('RStatusAkadSearch', function ($resource) {
        return $resource('api/_search/rStatusAkads/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
