'use strict';

angular.module('aplikasiApp')
    .factory('RPendidikanSearch', function ($resource) {
        return $resource('api/_search/rPendidikans/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
