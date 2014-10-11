/***
**  JQUERY MINIFBALBUM PLUGIN
**	WRITTEN BY TOM ROMBAUT
**  Version 0.1
**  06/2012
**  DEPENDENCIES : JQUERY 1.3.1+ 
***/
var MiniFbAlbum = function(element, options) {
	var conf = $.extend({}, $.fn.minifbalbum.defaults, options);
	var fbalbum = $(element);
	var currentIndex = 0;
	var cache = {};
	
	var _initialize = function(){
		fbalbum.addClass("fba");
		if(conf.pageID){
			_initAlbums();
		}else if(conf.albumID){
			initAlbums({id:conf.albumID},false);
		}
	};
	
	var _initAlbums = function(){
		if(cache.albums){
			_loadAlbums(cache["albums"]);
		}else{
			var pageID = "me";
			if(conf.pageID)
				pageID = conf.pageID;
			var url = conf.graphurl+pageID+"/albums?access_token="+conf.token+"&expires_in=0&callback=?";
			$.getJSON(url,
				    function(ob){
						if($.isFunction(conf.onload)){
							conf.onload(fbalbum);
						}
						_loadAlbums(ob.data);
						cache["albums"] = ob.data;
						if(ob.paging && ob.paging.next){
							_checknextcount(ob.paging.next,_addAlbums);
						}
					}
			);
		}
	}
	var _loadAlbums = function(albums){
		fbalbum.html("");
		_addTitle(conf.title);
		if(albums && albums.length > 0){
			for(var i=0; i < albums.length; i++){
				if(albums[i].count && albums[i].count > 0){
					if($.inArray(albums[i].type,conf.types) != -1){
						_createAlbum(albums[i]);
					}
				}
			}
		}else{
			fbalbum.html("Geen fotoalbums gevonden");
		}
	}
	var _addAlbums = function(ob){
		var albums = ob.data;
		for(var i=0; i < albums.length; i++){
			if(albums[i].count && albums[i].count > 0){
				if($.inArray(albums[i].type,conf.types) != -1){
					_createAlbum(albums[i]);
					cache["albums"][cache["albums"].length] = albums[i];
				}
			}
		}
		if(ob.paging && ob.paging.next){
			_checknextcount(ob.paging.next,_addAlbums);
		}
	}
	
	
	
	var _addTitle = function(strTitle){
		var title = $("<"+conf.titleEl+">"+strTitle+"</"+conf.titleEl+">");
		fbalbum.append(title);
	};
	var _createAlbum = function(album){
		
		var ob = $("<div class='album-thumb'><div class='album-overlay'></div><div class='inner'></div><div class='album-title'></div></div>");
		$(".album-overlay",ob).css("opacity","0.8");
		ob.hover(function(){
			$(this).addClass("album-thumb-hover");
			_albumOverlayStart($(".album-overlay",$(this)));
		},function(){
			$(this).removeClass("album-thumb-hover");
			_albumOverlayEnd($(".album-overlay",$(this)));	
		});
		
		var tempname = album.name;
		if(album.name.length > 30){
			tempname = album.name.substring(0,30);
		}
		$(".album-title",ob).html(tempname);
		ob.click(function(){
			$(this).addClass("loading");
			_initAlbumImages(album,$(this));
		});
		fbalbum.append(ob);
		_preloadImage(ob,conf.graphurl+album.cover_photo+"/picture?type="+conf.thumbsize+"&access_token="+conf.token);
	};
	
	var _albumOverlayStart = function(overlay){
		overlay.animate({height	:	0},150);
	}
	var _albumOverlayEnd = function(overlay){
		if(!overlay.parent(0).hasClass("loading")){
			overlay.animate({height	:	overlay.parent(0).innerHeight()},150);
		}
	}
	var _preloadImage = function(ob,img){
		var image   = $(new Image());
		image.attr('src',img);
		if(image.attr("width")){
			_showThumb(ob,img);
			image.remove();
		}else{
			image.load(function(){
				_showThumb(ob,img);
				image.remove();
			});
		}
	}
	
	var _showThumb = function(ob,img){
		$(".inner",ob).css({
			background	:	"transparent url("+img+") no-repeat center center"
		});
	};
	
	var _initAlbumImages = function(album,trigger){
		$(".album-thumb",fbalbum).each(function(){
			if(trigger && trigger.get(0) != this){
				$(this).css("opacity","0.2");
			}else{
				$(".inner",$(this)).fadeOut(100);
			}
		});
		
		var pageID = "me";
		if(conf.pageID)
			pageID = conf.pageID;
		var url = conf.graphurl+album.id+"/photos?access_token="+conf.token;
		_loadImages(album,url);
	};
	var _loadImages = function(album,url){
		url += "&expires_in=0&callback=?";
		$.getJSON(url,
			function(ob){
				var imgTimeout 	= 0;
				var init		= false;
				if($(".album-thumb",fbalbum).length > 0){
					$(".album-thumb",fbalbum).fadeOut(100,function(){$(this).remove();});
					imgTimeout = 100;
					init = true;
				}
				if(ob.data && ob.data.length > 0){
					setTimeout(	function(){
						if(init){
							fbalbum.html("");
							_addTitle(album.name);
						}
						for(i=0;i<ob.data.length;i++){
							_createImage(ob.data[i]);
						}
						
						if(init){
							var btn = $("<div class='image-thumb album-return'><div class='inner-back'>"+conf.returntext+"</div></div>");
							fbalbum.append(btn);
							btn.css("opacity","0.5");
							btn.hover(function(){
								$(this).css("opacity","1");
							},function(){
								$(this).css("opacity","0.5");
							});
							btn.click(function(){
								$(".album-thumb",fbalbum).fadeOut(250,function(){$(this).remove();});
								setTimeout(	function(){
									_initAlbums();
								},250);
							});	
						}
						if(ob.paging && ob.paging.next && ob.data.length > 0){
							var more_btn = $("<div class='image-thumb-full'><div class='inner-back'>"+conf.moretext+"</div></div>");
							fbalbum.append(more_btn);
							more_btn.css({
								opacity	:	"0.5",
								display	:	"none"
							});
							_checknextcount(ob.paging.next,more_btn);
							more_btn.hover(function(){
								$(this).css("opacity","1");
							},function(){
								$(this).css("opacity","0.5");
							});
							more_btn.click(function(){
								more_btn.fadeOut(250,function(){$(this).remove();});
								setTimeout(	function(){
									_loadImages(album,ob.paging.next);
								},250);
							});	
						}
					},imgTimeout);
				}
		});
	}
	var _checknextcount = function(url,eltoshow){
		url += "&expires_in=0&callback=?";
		$.getJSON(url,
			function(ob){
				if(ob.data && ob.data.length > 0){
					if($.isFunction(eltoshow)){
						eltoshow(ob);
					}else{
						eltoshow.css("display","block");
					}
				}else{
					if(!$.isFunction(eltoshow)){
						eltoshow.remove();
					}
				}
			});
	}
	
	var _createImage = function(image,init){
		var ob = $("<div class='image-thumb'><div class='inner'></div></div>");
		ob.hover(function(){
			$(this).addClass("image-thumb-hover")
		},function(){
			$(this).removeClass("image-thumb-hover");	
		});
		ob.click(function(){
			currentIndex = $(".image-thumb",$(this).parent(0)).index($(this));
			_scaleImage(image);
		});
		if($(".album-return",fbalbum).length > 0){
			$(".album-return",fbalbum).before(ob);
		}else{
			fbalbum.append(ob);
		}
		_preloadImage(ob,image.picture);
	}
	
	var _scaleImage = function(img){
		
		var image   = $(new Image());
		if(img.source){
			image.attr('src',img.source);
		}else{
			image.attr('src',img.images[0].source);
		}
		if(image.attr("width")){
			_showScaledImage(img,image.attr("width"),image.attr("height"));
			image.remove();
		}else{
			image.load(function(){
				_showScaledImage(img,image.attr("width"),image.attr("height"));
				image.remove();
			});
		}
	}
	var _nextImage = function(){
		currentIndex = currentIndex+1;
		
		var max = $(".image-thumb",fbalbum).length - 1;
		if(currentIndex >= max){
			currentIndex = 0;
		}
		var tIndex = 0;
		$(".image-thumb",fbalbum).each(function(){
			if(tIndex == currentIndex){
				$(this).trigger("click");
			}
			tIndex++;
		});
	};
	var _prevImage = function(){
		currentIndex = currentIndex -1;
		if(currentIndex < 0){
			currentIndex = ($(".image-thumb",fbalbum).length - 2);
		}
		var tIndex = 0;
		$(".image-thumb",fbalbum).each(function(){
			if(tIndex == currentIndex){
				$(this).trigger("click");
			}
			tIndex++;
		});
		//fbalbum.children(".image-thumb:eq("+currentIndex+")").trigger("click");
	};
	var _showScaledImage = function(img,w,h){
		var imgSrc = "";
		if(img.source){
			imgSrc = img.source;
		}else{
			imgSrc = img.images[0].source;
		}
		
		if($(".fba-overlay").length == 0){
			$("body").append($("<div class='fba-overlay'></div>"));
			$(".fba-overlay").css("opacity","0.8");
		}
		var exists = false;
		if($(".fba-popup").length > 0){
			$(".fba-popup img").fadeOut(250,function(){$(this).remove();});
			popup = $(".fba-popup");
			exists = true;
		}else{
			popup = $("<div class='fba-popup'></div>");
			$("body").append(popup);
		}
		var winh	=	$(window).height();
		var winw	=	$(window).width();
		var left 	= 	(winw - w)/2;
		var top		= 	(winh - h)/2;
		popup.animate({
			visibility	:	"visible",
			left		:	left,
			top			:	top,
			width		:	w,
			height		:	h
		},500,function(){
			var nimg = $('<img src="'+imgSrc+'" />');
			nimg.css("display","none");
			$(this).append(nimg);
			nimg.fadeIn();
		});
		/*
			popup.css("visibility","hidden");
		*/
		if(!exists){
			$(document).keydown(function(e){
				if(e.keyCode == 27){
					_closeScaledImage();
				}
				if(e.keyCode == 37){
					_prevImage();
				}
				if(e.keyCode == 39){
					_nextImage();
				}
			});
			$(".fba-overlay").click(function(){
				_closeScaledImage();
			});
		}
	}	
	var _closeScaledImage = function(){
		$(".fba-overlay,.fba-popup").fadeOut(250,function(){$(this).remove()});
		$(document).unbind("keydown");
	}
	
	
	_initialize();
	return fbalbum;
};

/**
**	JQUERY PLUGIN
**/
(function($) {	
	$.fn.minifbalbum = function(options) {
		return this.each(function() {
			var element = $(this);
			if (element.data('minifbalbum')) return;
			var w = new MiniFbAlbum(this, options);
			element.data('minifbalbum', w);
		});
	};
})(jQuery);

$.fn.minifbalbum.defaults = {
	token				:	'',
	graphurl			:	'https://graph.facebook.com/',
	thumbsize			:	'normal',
	types				:	["normal","profile","wall"],
	onload				:	false,
	pageID				:	"",
	returntext			:	"Terug naar albums",
	moretext			:	"Meer...",
	titleEl				:	"h2",
	title				:	"&nbsp;"
};