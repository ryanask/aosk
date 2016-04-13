'use strict';

angular.module('aplikasiApp')
    .factory('RKantorSearch', function ($resource) {
        return $resource('api/_search/rKantors/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
