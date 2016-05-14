<html>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<body>
<?php
//계정 정보 
$mysql_host = 'localhost';
$mysql_user = 'footprint';
$mysql_password ='vntvmflsxm';
$mysql_db = 'footprint';

//접속 
$connect=mysql_connect(  $mysql_host, $mysql_user, $mysql_password);
$dbconn = mysql_select_db($mysql_db, $connect);
//charset 설정
mysql_set_charset(utf8);
//쿼리
$lat = "select latitude from $mysql_db";
$lng = "select longitude from $mysql_db";

$result1 = mysql_query($lat, $connect);
$result2 = mysql_query($lng, $connect);
$row = mysql_fetch_array($result1);
$col = mysql_fetch_array($result2);
echo $row['latitude'];
echo "\n";
echo $col['longitude'];
/*
if($row) {
	echo $row['latitude'];
	echo "\n성공";
} else {
	echo "row실패";
}*/

/*
$query= "select * from images where id=$id" ;
$result=mysql_query($query,$connect );
$row=mysql_fetch_array($result);

  	echo $row[];
*/


mysql_close();

?>
</body>
</html>
