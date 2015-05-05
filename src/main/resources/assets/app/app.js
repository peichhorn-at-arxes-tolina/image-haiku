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

    angular.module('ImageHaikuApp', ['ngRoute', 'autoheight', 'ImageHaikuApi'])
        .config(function ($routeProvider) {
            $.material.init();

            $routeProvider
                .when('/', {
                    templateUrl: '/app/haiku.html',
                    controller: 'RandomHaikuController',
                    resolve: {
                        haiku: function ($q, Api) {
                            var defer = $q.defer();
                            Api.Haiku.get({}, defer.resolve);
                            return defer.promise;
                        }
                    }
                })
                .when('/haiku/:haikuId', {
                    templateUrl: '/app/haiku.html',
                    controller: 'HaikuController',
                    resolve: {
                        haiku: function ($q, $route, Api) {
                            var defer = $q.defer();
                            var routeParams = $route.current.params;
                            Api.Haiku.get({haikuId: routeParams.haikuId}, defer.resolve);
                            return defer.promise;
                        }
                    }
                })
                .when('/new', {
                    templateUrl: '/app/new.html',
                    controller: 'NewController'
                })
                .otherwise({
                    redirectTo: '/'
                });
        })
        .controller('RandomHaikuController', function ($scope, haiku, Api) {
            var renewHaiku = function() {
                Api.Haiku.get({}, setHaiku);
            };
            var setHaiku = function(h) {
                $scope.haiku = h;
                window.setTimeout(renewHaiku, 5000);
            };
            setHaiku(haiku);
        })
        .controller('HaikuController', function ($scope, haiku) {
            $scope.haiku = haiku;
        })
        .controller('NewController', function ($scope, $location, Api) {
            $scope.haiku = new Api.Haiku();

            $scope.isIncomplete = function() {
                return !$scope.haiku.image || !$scope.haiku.text || !$scope.haiku.author;
            };

            $scope.submit = function () {
                $scope.haiku.$save(function(data) {
                    $location.path("/haiku/" + data.id)
                });
            };
        }).directive('dropzone', function () {
            return {
                restrict: 'A',
                require: '?ngModel',
                link: function (scope, element, attr, ngModel) {
                    var previewNode = element[0].querySelector(".template");
                    var previewTemplate = previewNode.parentNode.innerHTML;
                    previewNode.parentNode.removeChild(previewNode);

                    var dropzone = new Dropzone(element[0], {
                        url: "/api/image",
                        paramName: "file",
                        maxFiles: 1,
                        thumbnailWidth: 100,
                        thumbnailHeight: 100,
                        previewsContainer: ".dz-preview",
                        previewTemplate: previewTemplate,
                        acceptedFiles: 'image/*',
                        maxfilesexceeded: function (file) {
                            this.removeAllFiles();
                            this.addFile(file);
                        }
                    });
                    dropzone.on("success", function (file) {
                        ngModel && ngModel.$setViewValue(file.name);
                    });
                    dropzone.on("addedfile", function () {
                        var node = element[0].querySelector(".dz-message");
                        node && node.parentNode.removeChild(node);
                    });
                }
            };
        });
})(window, window.angular);
