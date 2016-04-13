'use strict';

angular.module('aplikasiApp')
    .factory('RStatusRekeningSearch', function ($resource) {
        return $resource('api/_search/rStatusRekenings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
