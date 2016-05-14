
<?php


$mysql_hostname = "localhost";
$mysql_username = "footprint";
$mysql_password = "vntvmflsxm";
$mysql_database = "footprint";

mysql_connect($mysql_hostname, $mysql_username, $mysql_password);
mysql_select_db($mysql_database);
mysql_set_charset("utf8");

  $id = @$_POST["id"];


//  mysql_query("SELECT footprint.* FROM footprint WHERE
//    '$latitude', '$longitude');");


$result = mysql_query("SELECT footprint.*
FROM footprint WHERE id=\"" $id "\";");
$values = mysql_fetch_array($result);
echo $values["date"]  + "</br>";
echo $values["time"] + "</br>";
echo $values["article"] + "</br>";
echo $values["filename"] + "</br>";
echo $values["latitude"] + "</br>";
echo $values["longitude"] + "</br>";


mysql_close($connect);

 ?>
