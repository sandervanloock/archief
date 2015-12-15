var app = angular.module('app',
  ['ngRoute', 'appControllers', 'eventService', 'photos', 'users', 'groupService', 'security', 'arrayFilters',
    'ui.bootstrap', 'angularSpinner', 'services.config', 'lvl.directives.fileupload', 'ngAutocomplete']);

angular.module('app').run(['security', function (security) {
  // Get the current user when the application starts
  // (in case they are still logged in from a previous session)
  security.requestCurrentUser();
}]);

app.config(['$routeProvider', 'securityAuthorizationProvider', function ($routeProvider, securityAuthorizationProvider) {
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
}]);

app.directive('monthYearInput', function () {
  return {
    restrict: 'A',
    require: 'ngModel',
    link: function (scope, element, attrs, ngModelCtrl) {
      element.datetimepicker({
        format: "MM/yyyy",
        viewMode: "months",
        minViewMode: "months",
        pickTime: false,
        language: 'nl',
        weekStart: 1
      }).on('changeDate', function (e) {
        ngModelCtrl.$setViewValue(e.date);
        scope.$apply();
      });
    }
  };
});
