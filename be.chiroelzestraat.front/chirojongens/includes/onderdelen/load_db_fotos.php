<?php 
echo "Hello world <br />";
$ftpServer = "ftp.chiroelzestraat.be"; 
$ftpUser = "chiroelzestraat.be"; 
$ftpPass = "leider"; 

set_time_limit(160); 

$conn = @ftp_connect($ftpServer) 
or die("Couldn't connect to FTP server"); 

$login = @ftp_login($conn, $ftpUser, $ftpPass) 
or die("Login credentials were rejected"); 

$workingDir = ftp_pwd($conn).'archief'; 

@ftp_chdir($conn,$workingDir);

echo "Files for directory: $workingDir<br><br>"; 

$fList = @ftp_nlist($conn, $workingDir); 

if(is_array($fList)) 
{ 
	for($i = 0; $i < sizeof($fList); $i++) 
	{ 
		$file_size = ftp_size($conn, $fList[$i]);
		if($file_size == -1)
		{
			echo '<a href="http://www.chiroelzestraat.be'.$workingDir.'/'.$fList[$i].'">'.$fList[$i]."</a><br>";
		} 
	} 
} 
else 
{ 
	echo "$workingDir contains no files."; 
} 

ftp_quit($conn); 

?> 