'use strict';

angular.module('myApp.cookieService', ['ngCookies'])

    .factory('cookieService', ['$cookieStore', function ($cookies) {
        return {
            getCookie:  function(name){
                return $cookies.get(name);
            },
            storeCookie: function(name, value, exp){
                $cookies.put(name,value);
            }
        }
    }]);