angular.module('app').controller('CarouselDemoCtrl', function ($scope) {

    $scope.myInterval = 5000;
    var slides = $scope.slides = [];

    $scope.addSlide = function(src, text) {
        slides.push({
            image: src,
            text: text
        });
    };
    $scope.addSlide("images/Verhuur/original/01010003.JPG",'Groot Heem');
    $scope.addSlide("images/Verhuur/original/01010004.JPG",'Keuken (1)');
    $scope.addSlide("images/Verhuur/original/01010006.JPG",'Keuken (2)');
    $scope.addSlide("images/Verhuur/original/01010010.JPG",'Koer');
    $scope.addSlide("images/Verhuur/original/01010013.JPG",'WC + Sanitair');
});