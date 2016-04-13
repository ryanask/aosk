'use strict';

angular.module('aplikasiApp')
    .factory('RKolektibilitasSearch', function ($resource) {
        return $resource('api/_search/rKolektibilitass/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
