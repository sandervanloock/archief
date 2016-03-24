angular.module('app').directive('pdfSlider', ['$http', '$timeout', 'Properties', function ($http, $timeout, properties) {

    return {
        restrict: 'A',
        templateUrl: 'templates/pdf-slider.tpl.html',
        link: function (scope, element, attrs) {
            $http.get(properties.apiHost + "/admin/api/program/latest").then(function (response) {
                scope.images = response.data.file.images;
                scope.$$postDigest(function(){
                    console.log(scope.images);
                    console.log($(element).find('.flipbook'));
                    $timeout(function () {
                        $(element).find('.flipbook').bxSlider({
                            'adaptiveHeight': true,
                            'startSlide': 0,
                            'mode': 'fade'
                        });
                    }, 0);
                });
            })
        }
    }
}]);