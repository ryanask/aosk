'use strict';

angular.module('aplikasiApp')
    .factory('RMaritalStatusSearch', function ($resource) {
        return $resource('api/_search/rMaritalStatuss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
