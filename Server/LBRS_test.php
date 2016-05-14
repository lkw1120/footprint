
<?php


$mysql_hostname = "localhost";
$mysql_username = "footprint";
$mysql_password = "vntvmflsxm";
$mysql_database = "footprint";

mysql_connect($mysql_hostname, $mysql_username, $mysql_password);
mysql_select_db($mysql_database);
mysql_set_charset("utf8");

  $latitude = @$_POST["latitude"];
  $longitude = @$_POST["longitude"];


//  mysql_query("SELECT footprint.* FROM footprint WHERE
//    '$latitude', '$longitude');");


$result = mysql_query("SELECT footprint.*,
	(6371*acos(cos(radians('$latitude'))*cos(radians(slLat))*cos(radians(slLng)
	-radians('$longitude'))+sin(radians('$latitude'))*sin(radians(slLat)))) AS distance
FROM footprint HAVING distance <= 3
ORDER BY distance LIMIT 0,1000;");
$values = mysql_fetch_array($result);
for(($i=0;$i = mysql_fetch_array($result);$i++) {
  echo $values["id"] + " " + $values["latitude"] + " " + $values["longitude"] + "</br>";
}


mysql_close($connect);
 ?>
