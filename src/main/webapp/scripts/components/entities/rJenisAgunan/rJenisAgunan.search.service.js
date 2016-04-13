'use strict';

angular.module('aplikasiApp')
    .factory('RJenisAgunanSearch', function ($resource) {
        return $resource('api/_search/rJenisAgunans/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
