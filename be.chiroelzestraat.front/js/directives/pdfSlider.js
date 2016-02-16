angular.module('app').directive('pdfSlider', ['$http','Properties', function ($http,properties) {

    function getSize() {
        console.log('get size');
        var width = $(".container").width();
        var height = 500;

        return {
            width: width,
            height: height
        }
    }
    window.addEventListener('resize', resize);
    function resize() {
        console.log('resize event triggered');

        var size = getSize();
        console.log(size);

        if (size.width > size.height) { // landscape
            $('.flipbook').turn('display', 'double');
        }
        else {
            $('.flipbook').turn('display', 'single');
        }

        $('.flipbook').turn('size', size.width, size.height);
    }

    return {
        restrict: 'A',
        templateUrl: 'templates/pdf-slider.tpl.html',
        link: function (scope, element, attrs) {
            $http.get(properties.apiHost + "/admin/api/program/latest").then(function (response) {
                scope.images = response.data.file.images;
                scope.$$postDigest(function () {
                    var size = getSize();
                    $(element).find('.flipbook').turn({

                        // Width
                        width: size.width,

                        // Height
                        height: size.height,

                        // Elevation
                        //elevation: 50,

                        // Enable gradients
                        //gradients: true,

                        // Auto center this flipbook
                        autoCenter: true

                    });
                })
            })
        }
    }
}])