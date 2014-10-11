var MiniLoader = function(element,options) {
	var element = $(element);
	var conf = $.extend({}, $.fn.startminiload.defaults, options);
	var loading = false;
	var timeouts = [];
	var wrapper = false;
	var overlay = false;
	var text	= false;
	
	var _initialize = function(){
		var offset_top 	= Math.floor((element.height() - conf.size)/2);
		var zindex		= 1000;
		if(element.css("position") != "absolute")
			element.css("position","relative");
		
		if(conf.overlay == true){
			overlay = $("<div></div>").css({
				position		:	"absolute",
				zIndex			:	zindex,
				width			:	"100%",
				height			:	"100%",
				backgroundColor	:	conf.overlaycolor,
				opacity			:	conf.overlayopacity
			});
			element.prepend(overlay);
		}
		wrapper = $("<div></div>").css({
			position	:	"absolute",
			overflow	:	"hidden",
			width		:	"100%",
			height		:	conf.size,
			top			:	offset_top,
			left		:	0,
			zIndex		:	zindex + 1
		});
		element.prepend(wrapper);
		if(conf.text){
			text = $("<div></div>").css({
				position	:	"absolute",
				overflow	:	"hidden",
				width		:	"100%",
				top			:	offset_top + conf.size,
				left		:	0,
				textAlign	:	"center",
				color		:	conf.textcolor,
				zIndex		:	zindex + 1
			}).html(conf.text);
			element.append(text);
		}
		
		for(var i=0;i<conf.count;i++){
			var block = $("<div></div>").css({
				position	:	"absolute",
				width		:	conf.size,
				height		:	conf.size,
				backgroundColor:conf.color,
				marginLeft	:	-conf.size,
				top			:	0
			});
			wrapper.prepend(block);
		}
	}
	element.refresh = function(){
		for(var i =0;i<timeouts.length;i++){
			clearTimeout(timeouts[i]);
		}
		timeouts = [];
		wrapper.children().each(function(){
			$(this).css("marginLeft",-conf.size);
		});
		element.start();
	}
	element.start = function(){
		if(!loading){
			_initialize();
		}
		var count = 0;
		wrapper.children().each(function(){
			var block = $(this);
			if(conf.fade){
				block.css("opacity",0);
			}
			var nr = count;
			timeouts[timeouts.length] = setTimeout(function(){
				if(!wrapper)
					return;
				var ml = (Math.round(wrapper.width()/2) - ((conf.size * nr) + (nr*conf.space)));
				block.animate({
					marginLeft	:	ml,
					opacity		:	1
				},conf.speed,"",function(){
					block.animate({
						marginLeft	:	ml + ((conf.count-nr)*conf.size)
					},(((conf.timeout)*(conf.count-2))));
					timeouts[timeouts.length] = setTimeout(function(){
						block.animate({
							marginLeft	:	Math.round(wrapper.width() + conf.size),
							opacity		:	conf.fade?0:1
						},conf.speed,function(){
							if(nr == (conf.count-1)){
								element.refresh();
							}
						});
					},((conf.count-2) * conf.timeout));
				});
			},(nr*conf.timeout));
			count++;
		});
		loading = true;
	}
	element.stop = function(){
		if(loading){
			for(var i =0;i<timeouts.length;i++){
				clearTimeout(timeouts[i]);
			}
			wrapper.children().stop();
			if(overlay)
				overlay.remove();
			if(text)
				text.remove();
			wrapper.remove();
			wrapper =	false;
			overlay =	false;
			text	=	false;
			loading = 	false;
			timeouts= 	[];
		}
	}
	return element;
};

/**
**	JQUERY MINILOADER
**/	
(function($) {	
	$.fn.startminiload = function(options) {
		return this.each(function() {
			var element = $(this);
			if (element.data('miniloader')){
				element.data('miniloader').start();
				return;
			}
			var w = new MiniLoader(this, options);
			w.start();
			element.data('miniloader', w);
		});
	};
	$.fn.endminiload = function() {
		return this.each(function() {
			var element = $(this);
			if (element.data('miniloader')){
				element.data('miniloader').stop();
			}
			return;
		});
	};
})(jQuery);

$.fn.startminiload.defaults = {
	timeouts		:	[],
	size 			: 	5,
	count			:	5,
	space			:	5,
	fade			:	false,
	color			: 	"#EA48CD",
	speed			:	1000,
	timeout			:	250,
	overlay			:	true,
	overlaycolor	:	"#0D0D0D",
	overlayopacity	:	"0.5"
};