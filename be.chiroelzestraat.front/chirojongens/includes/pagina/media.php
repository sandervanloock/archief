<script type="text/javascript">
	$(document).ready(function(){
		$("#gallery-content").startminiload({color:"#00688F"});
		$("#gallery-content").minifbalbum({
			token		:	"",
			pageID	:	"234251636629703",
			types		:	["normal"],
			onload		:	function(fbalbum){
				$("#gallery-content").endminiload();
			}
		});
		
	});
</script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#playeryou').youTubeChannel({user:'breezer00758'});
	});	
</script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#playeryou').youTubeChannel({user:'josdemosselman'});
	});	
</script>

<div id="mediaPanel" class="TabbedPanels" >
  <ul class="TabbedPanelsTabGroup">
    <li class="TabbedPanelsTab" tabindex="0">
    	<img src="images/fotocamera.png" alt="Fotokanaal" class="icon"/>
    	<strong class="orangeTitle">Fotokanaal</strong></br>
    	<strong class="subTitle">Allerlei chirofoto's</strong></br>
		<img src="css/images/pijl-lijst.gif"></img>
    </li>
    <li class="TabbedPanelsTab" tabindex="1">
    	<img src="images/filmcamera.png" alt="Filmkanaal" class="icon"/>
    	<strong class="orangeTitle">Filmkanaal</strong></br>
    	<strong class="subTitle">Allerlei chirofilmpjes</strong></br>
		<img src="css/images/pijl-lijst.gif"></img>
    </li>
    <li class="TabbedPanelsTab" tabindex="2">
    	<img src="images/archief.png" alt="Archief" class="icon"/>
    	<strong class="orangeTitle">Archief</strong></br>
    	<strong class="subTitle">Oude foto's en filmpjes</strong></br>
		<img src="css/images/pijl-lijst.gif"></img>
    </li>
  </ul>
  
  
  <div class="TabbedPanelsContentGroup" >
    <div class="TabbedPanelsContent">
    	<div class='grid' id='photochannel'>
			<h1>Ons Fotokanaal</h1>
            <p style="font-style:italic; font-size:10px;">Gebruik je pijltjes om door het album te bladeren. <br  />Voor gebruikers van Internet Explorer 7 en 8, gelieve op de site van <a href="http://www.facebook.com/pages/Chirojongens-Elzestraat/234251636629703?ref=ts&sk=photos" >facebook</a> te kijken aub.</p>
			<div id='gallery-content' class="TabbedPanelsContent"></div>
		</div> 
    </div>
    <div class="TabbedPanelsContent">	
    	<div class="grid TabbedPanelsContent" id='videochannel' style="min-height:500px;">
			<h1>Ons Videokanaal</h1>
			<div id="videotitle"></div>
			<div id="playeryou"></div>
		</div>
    </div>
    <div class="TabbedPanelsContent">	
    	<div class="grid TabbedPanelsContent">
			<iframe src="http://archief.chiroelzestraat.be" frameborder="0" style="height:800px; width:850px;"></iframe>
		</div>
    </div>
  </div>
</div>
<script type="text/javascript">
	var TabbedPanels1 = new Spry.Widget.TabbedPanels("mediaPanel");
</script>