'use strict';

angular.module('aplikasiApp')
    .factory('RStatusRekening', function ($resource, DateUtils) {
        return $resource('api/rStatusRekenings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
