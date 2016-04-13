'use strict';

angular.module('aplikasiApp')
    .factory('RSektorKurSearch', function ($resource) {
        return $resource('api/_search/rSektorKurs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
