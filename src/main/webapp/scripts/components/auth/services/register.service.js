'use strict';

angular.module('aplikasiApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


