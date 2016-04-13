'use strict';

angular.module('aplikasiApp')
    .factory('RJenisKelaminSearch', function ($resource) {
        return $resource('api/_search/rJenisKelamins/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
