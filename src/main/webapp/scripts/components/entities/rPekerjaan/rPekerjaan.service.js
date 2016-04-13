'use strict';

angular.module('aplikasiApp')
    .factory('RPekerjaan', function ($resource, DateUtils) {
        return $resource('api/rPekerjaans/:id', {}, {
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
