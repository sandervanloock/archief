var app = angular.module('app',
    [ 'ngRoute', 'appControllers', 'eventService', 'photos', 'users', 'groupService', 'security', 'arrayFilters', 'ui.bootstrap', 'angularSpinner', 'services.config', 'lvl.directives.fileupload', 'ngAutocomplete' ]);

angular.module('app').run(['security', function (security) {
    // Get the current user when the application starts
    // (in case they are still logged in from a previous session)
    security.requestCurrentUser();
}]);

app.config([ '$routeProvider', 'securityAuthorizationProvider', function ($routeProvider, securityAuthorizationProvider) {
    $routeProvider.when('/events', {
        templateUrl: 'template/event-list.html',
        controller: 'EventListCtrl',
        resolve: securityAuthorizationProvider.requireAdminUser
    }).when('/events/:eventId', {
            templateUrl: 'template/event-detail.html',
            controller: 'EventDetailCtrl',
            resolve: securityAuthorizationProvider.requireAdminUser
        }).when('/event/new', {
            templateUrl: 'template/event-create.html',
            controller: 'EventCreateCtrl',
            resolve: securityAuthorizationProvider.requireAdminUser
        }).when('/home', {
            templateUrl: 'template/archief.html',
            controller: 'ArchiefCtrl'
        }).otherwise({
            redirectTo: '/home'
        });
} ]);

app.directive('birthdayInput', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attrs, ngModelCtrl) {
            element.datetimepicker({
                format: "dd/MM/yyyy",
//                viewMode: "months",
//                minViewMode: "months",
                pickTime: false
            }).on('changeDate', function (e) {
                    ngModelCtrl.$setViewValue(e.date);
                    scope.$apply();
                });
        }
    };
});

app.directive('monthYearInput', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attrs, ngModelCtrl) {
            element.datetimepicker({
                format: "MM/yyyy",
                viewMode: "months",
                minViewMode: "months",
                pickTime: false
            }).on('changeDate', function (e) {
                    ngModelCtrl.$setViewValue(e.date);
                    scope.$apply();
                });
        }
    };
});


app.directive('moDateInput', function ($window) {
    return {
        require: '^ngModel',
        restrict: 'A',
        link: function (scope, elm, attrs, ctrl) {
            var moment = $window.moment;
            var dateFormat = attrs.moMediumDate;
            attrs.$observe('moDateInput', function (newValue) {
                if (dateFormat == newValue || !ctrl.$modelValue) return;
                dateFormat = newValue;
                ctrl.$modelValue = new Date(ctrl.$setViewValue);
            });

            ctrl.$formatters.unshift(function (modelValue) {
                scope = scope;
                if (!dateFormat || !modelValue) return "";
                var retVal = moment(modelValue).format(dateFormat);
                return retVal;
            });

            ctrl.$parsers.unshift(function (viewValue) {
                scope = scope;
                var date = moment(viewValue, dateFormat);
                return (date && date.isValid() && date.year() > 1950 ) ? date.toDate() : "";
            });
        }
    };
});

app.directive('moChangeProxy', function ($parse) {
    return {
        require: '^ngModel',
        restrict: 'A',
        link: function (scope, elm, attrs, ctrl) {
            var proxyExp = attrs.moChangeProxy;
            var modelExp = attrs.ngModel;
            scope.$watch(proxyExp, function (nVal) {
                if (nVal != ctrl.$modelValue)
                    $parse(modelExp).assign(scope, nVal);
            });
            elm.bind('blur', function () {
                var proxyVal = scope.$eval(proxyExp);
                if (ctrl.$modelValue != proxyVal) {
                    scope.$apply(function () {
                        $parse(proxyExp).assign(scope, ctrl.$modelValue);
                    });
                }
            });
        }
    };
});