'use strict';

angular.module('aplikasiApp')
    .factory('RJenisAgunan', function ($resource, DateUtils) {
        return $resource('api/rJenisAgunans/:id', {}, {
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
