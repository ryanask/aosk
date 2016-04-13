'use strict';

angular.module('aplikasiApp')
    .factory('RPekerjaanSearch', function ($resource) {
        return $resource('api/_search/rPekerjaans/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
