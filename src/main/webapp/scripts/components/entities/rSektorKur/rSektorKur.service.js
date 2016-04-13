'use strict';

angular.module('aplikasiApp')
    .factory('RSektorKur', function ($resource, DateUtils) {
        return $resource('api/rSektorKurs/:id', {}, {
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
