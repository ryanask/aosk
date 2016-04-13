 'use strict';

angular.module('aplikasiApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-aplikasiApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-aplikasiApp-params')});
                }
                return response;
            }
        };
    });
