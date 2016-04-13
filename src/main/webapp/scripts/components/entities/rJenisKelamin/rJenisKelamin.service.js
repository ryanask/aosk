'use strict';

angular.module('aplikasiApp')
    .factory('RJenisKelamin', function ($resource, DateUtils) {
        return $resource('api/rJenisKelamins/:id', {}, {
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
