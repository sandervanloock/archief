<?php
//mailscript dat wordt standaard geladen als er een post is verstuurd.
if (isset($_POST['submitknop'])) {
				$naam = $_POST['naam'];
				$email = $_POST['email'];
				$vraag = $_POST['vraag'];
				$inhoud = $_POST['inhoud'];
				
//als alles is ingevuld, mail opstellen en zenden				
				if(!($naam =='' || $email=='' || $vraag=='' || $inhoud=='')){
					$headers = "From: ".$email.""."\r\n"."Reply-To: ".$email.""."\r\n"."X-Mailer: PHP/".phpversion();
					if ($vraag=='Info Verhuur'){
						$ontvanger=	"verhuur@chiroelzestraat.be";
						$onderwerp=	"Vraag van de website over Verhuur";
					}else{
						$ontvanger= "leiding@chiroelzestraat.be";
						$onderwerp=	"Vraag van de website voor Algemene Informatie";
					}
					$bericht = "Er is een vraag vanop de website. Gelieve te beantwoorden aub. \n\n";
					$bericht .= $naam." zou graag ".$vraag." te weten komen\n\n";
					$bericht .=$inhoud;
					if (!mail($ontvanger, $onderwerp, $bericht, $headers)){
						echo "Er is een fout opgetreden. Probeer nogmaals. Indien het probleem blijft voortdoen, gelieve uw vraag naar het algemeen email-adres te sturen";
					}else{
						echo "Uw vraag is correct verzonden. U zal zo spoedig mogelijk antwoord krijgen.";
					}
					
				}else{
					echo "Uw vraag is niet verzonden. Gelieve alle velden in te vullen aub. <br />";
				}
	
	}else{



?>


<div id='algemeen_contact' class='grid' style='float:left; width:47%;'>
	<h1>Algemeen Contact</h1>
	<hr/><br />
	 <h3>Groepsleiders</h3>
	 Naam: Thomas Marien<br />
	 Gsmnummer: 0499 26 04 09<br />
	 Emailadres: thomas@chiroelzestraat.be<br />
		<br />
	 Naam: Tomas Van Rompaey<br />
	 Gsmnummer: 0496 25 44 37<br />
	 Emailadres: tomas@chiroelzestraat.be<br />	
	
	 <hr /><br />
	 <h3>Volwassen Begeleider</h3>
	 Naam: Dirk Feremans<br />
	 Gsmnummer: 0494 56 12 91<br />
	 Emailadres: dirk@chiroelzestraat.be<br />
	 <hr /><br />	
	 <h3>Verhuur</h3>
	 Naam: Sam Granata<br />
	 Gsmnummer: 0471 72 66 86<br />
	 Emailadres: verhuur@chiroelzestraat.be<br />
</div>


<div id='locatie' class='grid' style='float:right; width:47%;'>
	<p style="text-align:right; padding:6px 6px;">
    
    <iframe width="420" height="240" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="http://maps.google.com/maps?f=d&amp;source=s_d&amp;saddr=51.060258,4.483902&amp;daddr=&amp;geocode=&amp;sll=51.059914,4.484573&amp;sspn=0.001119,0.00284&amp;vpsrc=0&amp;hl=nl&amp;mra=mr&amp;ie=UTF8&amp;t=m&amp;ll=51.059914,4.484573&amp;spn=0.001618,0.003433&amp;z=18&amp;output=embed"></iframe><br /><small>
    <a href="http://maps.google.com/maps?f=d&amp;source=embed&amp;saddr=51.060258,4.483902&amp;daddr=&amp;geocode=&amp;sll=51.059914,4.484573&amp;sspn=0.001119,0.00284&amp;vpsrc=0&amp;hl=nl&amp;mra=mr&amp;ie=UTF8&amp;t=m&amp;ll=51.059914,4.484573&amp;spn=0.001618,0.003433&amp;z=18" style="color:#0000FF;text-align:left"></a></small>

	
    </p>
    <p style="position:relative; float:left; padding-top: 10px;">
	Chirojongens Elzestraat<br />Clemenceaustraat 111 (achter de kerk)<br />2860 Sint-Katelijne-Waver<br />
    </p>
</div>

<div style='clear:both;'>&nbsp;</div>

<div id='contactform' class="grid">
	  	<h1 style="padding-bottom:10px;">Contact Formulier</h1>
		 <form action="<?php echo $_SERVER['PHP_SELF']."?pagina=contact"?>" method="post" name="login">
        			<table cellspacing="5px">
                        <tr>
                        	<td>Uw Naam:</td>
	                        <td><input type="text" name="naam" /></td>
		                </tr>
                        <tr>
                        	<td>Uw Emailadres:</td>
	                        <td><input type="text" name="email" /></td>
	                    </tr>
		                <tr>
		                  	<td><input type="radio" name="vraag" id="a1" value="Info Verhuur"/>Verhuur</td>
		                    <td><input type="radio" name="vraag" id="a2" value="Info Algemeen" />Algemene informatie</td></tr>
						<tr>
	                        <td colspan="2"><textarea name="inhoud" cols="43" rows="4"></textarea></td>
						</tr>
						<tr>
    	                    <td style="text-align:left; float:left; margin:4px;"><input type="submit" name="submitknop" value="    Verstuur    " /></td>
                  		</tr>
					</table>
				</form>
	</div>
    <?php }?>