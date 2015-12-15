var JSONBuilder = {
		
	parseEvents: function(dataAsJson){
		var data = JSON.parse(dataAsJson);
		var result = [];
		for(var i=0;i<data.length;i++){
			result.push(new Event(data[i].name,data[i].start,data[i].end,data[i].photos));
		}
		return result;
	},

	createNivoSlider: function(photos){
		var html = "<div class='slider-wrapper theme-default'><div class='ribbon'></div><div class='nivoSlider'>"; 
		for(var i=0;i<photos.length;i++){
			html += "<img src='" + staticHost + photos[i].directory + "' alt='" +photos[i].title+ "'/>";
		}
		html += "</div></div>";
		return html;
	}		
};