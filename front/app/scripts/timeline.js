var timeline = {		
	timeline: {
		header: "Digitaal archief - Chiro Elzestraat",
		type: "default",
		
        text: "",
        date: []
	}
};

function addEventsToTimeline(events){
	for(var i=0;i<events.length;i++){
		timeline.timeline.date.push({
            startDate: new Date(moment(events[i].startDate)),
            endDate: new Date(moment(events[i].endDate)),
            headline:events[i].name,
            text:"<p></p>",
            asset: {
                "media": JSONBuilder.createNivoSlider(events[i].photos)
            }
		});
	}
}
