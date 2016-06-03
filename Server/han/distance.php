<?php
require("con_dbinfo.php");

function parseToXML($htmlStr)
{
$xmlStr=str_replace('<','&lt;',$htmlStr);
$xmlStr=str_replace('>','&gt;',$xmlStr);
$xmlStr=str_replace('"','&quot;',$xmlStr);
$xmlStr=str_replace("'",'&#39;',$xmlStr);
$xmlStr=str_replace("&",'&amp;',$xmlStr);
return $xmlStr;
}

// Opens a connection to a MySQL server
$connection=mysql_connect ('localhost', $username, $password);
if (!$connection) {
  die('Not connected : ' . mysql_error());
}

// Set the active MySQL database
$db_selected = mysql_select_db($database, $connection);
if (!$db_selected) {
  die ('Can\'t use db : ' . mysql_error());
}

$x = @$_POST["latitude"];
$y = @$_POST["longitude"];
$radian = @$_POST["radian"];
$minRcmd = @$_POST["minRcmd"];
$timeSpectrun = @$_POST["timeSpectrum"];
$minTime = @$_POST["minTime"];
$maxTime = @$_POST["maxTime"];
// Select all the rows in the markers table
if($timeSpectrum==0) {
  $query = "SELECT *, (6371*acos(cos(radians($x))*cos(radians(latitude))*cos(radians(longitude)-radians($y))+sin(radians($x))*sin(radians(latitude)))) AS distance FROM footprint WHERE footprint.count >= $minRcmd HAVING distance <= $radian ORDER BY distance LIMIT 0, 1000";
}
else {
  $query = "SELECT *, (6371*acos(cos(radians($x))*cos(radians(latitude))*cos(radians(longitude)-radians($y))+sin(radians($x))*sin(radians(latitude))))
   AS distance FROM footprint WHERE footprint.count >= $minRcmd AND footprint.writeTime >= $minTime AND footprint.writeTime <= $maxTime
    HAVING distance <= $radian ORDER BY distance LIMIT 0, 1000";
}
$result = mysql_query($query);
if (!$result) {
  die('Invalid query: ' . mysql_error());
}

//header("Content-type: text/xml");

// Start XML file, echo parent node
//echo '<markers>';

// Iterate through the rows, printing XML nodes for each
while ($row = @mysql_fetch_assoc($result)){
  // ADD TO XML DOCUMENT NODE
  //echo '<marker ';
	//echo '<p>';
  echo ''. parseToXML($row['id']) .';';
  echo ''. $row['latitude'] . ';';
  echo '' . $row['longitude'] . ';';
  echo '' . $row['count'] . ';';
  echo '';
//echo '/>';

}

echo 'END';
// End XML file
//echo '</markers>';

mysql_close($connection);
?>
