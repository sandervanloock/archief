angular.module('resources.events', ['mysqlResource']);
angular.module('resources.events')

.factory('Events', ['mysqlResource', function ($mysqlResource) {

  var Events = $mysqlResource('events');

  return Events;
}]);