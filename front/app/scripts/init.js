$(document).ready(function() {
	$.ajax({
		url : backendHost + "/public/events/getAllEventPhotos",
		success : function(data) {
			var events = JSONBuilder.parseEvents(data);
			addEventsToTimeline(events);
			createStoryJS({
				type : 'timeline',
				source : timeline,
				embed_id : 'timeline',
				debug : true,
				css: 'styles/vendor/timeline/timeline.css',
				js: 'scripts/vendor/timeline/timeline.js'
			});
		}
	});
});

