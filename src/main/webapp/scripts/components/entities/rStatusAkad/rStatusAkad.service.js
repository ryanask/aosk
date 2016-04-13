'use strict';

angular.module('aplikasiApp')
    .factory('RStatusAkad', function ($resource, DateUtils) {
        return $resource('api/rStatusAkads/:id', {}, {
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
