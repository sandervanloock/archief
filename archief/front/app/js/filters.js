var filterModule = angular.module('arrayFilters', []);
filterModule.filter('pagination', function() {

	return function(inputArray, selectedPage, pageSize) {
		if (inputArray && pageSize>0) {
			var start = (selectedPage-1) * pageSize;
			return inputArray.slice(start, start + eval(pageSize));
		}
	};
});

filterModule.filter('range', function() {
	  return function(input, total) {
	    total = parseInt(total);
	    for (var i=0; i<total; i++)
	      input.push(i);
	    return input;
	  };
	});
