'use strict';

angular.module('aplikasiApp')
    .factory('errorHandlerInterceptor', function ($q, $rootScope) {
        return {
            'responseError': function (response) {
                if (!(response.status == 401 && response.data.path.indexOf("/api/account") == 0 )){
	                $rootScope.$emit('aplikasiApp.httpError', response);
	            }
                return $q.reject(response);
            }
        };
    });