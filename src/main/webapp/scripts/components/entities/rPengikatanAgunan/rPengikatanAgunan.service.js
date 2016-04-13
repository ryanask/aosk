'use strict';

angular.module('aplikasiApp')
    .factory('RPengikatanAgunan', function ($resource, DateUtils) {
        return $resource('api/rPengikatanAgunans/:id', {}, {
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
