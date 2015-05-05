(function (window, angular) {
    'use strict';

    angular.module('ImageHaikuApi', ['ngResource'])
        .factory('Api', ['$resource',
            function ($resource) {
                return {
                    Haiku: $resource('/api/haiku/:haikuId'),
                    Image: $resource('/api/image/:imageId')
                };
            }]);

    angular.module('ImageHaikuApp', ['ngRoute', 'ImageHaikuApi'])
        .config(function ($routeProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: '/app/haiku.html',
                    controller: 'HaikuController',
                    resolve: {
                        haiku: function ($q, Api) {
                            var defer = $q.defer();
                            Api.Haiku.get({}, defer.resolve);
                            return defer.promise;
                        }
                    }
                })
                .otherwise({
                    redirectTo: '/'
                });
        })
        .controller('HaikuController', function ($scope, haiku) {
            $scope.haiku = haiku;
        });
})(window, window.angular);
