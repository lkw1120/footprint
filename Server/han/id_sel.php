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

$connection=mysql_connect ('localhost', $username, $password);
if (!$connection) {
  die('Not connected : ' . mysql_error());
}

$db_selected = mysql_select_db($database, $connection);
if (!$db_selected) {
  die ('Can\'t use db : ' . mysql_error());
}
mysql_set_charset(utf8);

$id_select = (int)@$_POST["id"];

// Select all the rows in the markers table
$query = "SELECT footprint . *
FROM footprint.footprint
WHERE id = $id_select";

$result = mysql_query($query);
if (!$result) {
  die('Invalid query: ' . mysql_error());
}

while ($row = @mysql_fetch_assoc($result)){
  echo '' . parseToXML($row['id']) . ',';
  echo '' . $row['writeDate'] . ',';
  echo '' . $row['writeTime'] . ',';
  echo '' . $row['article']. ',';
  echo '' . $row['filename'] .',';
  echo '' . $row['latitude'] . ',';
  echo '' . $row['longitude'] . ',';
  echo 'END';

}

mysql_query("UPDATE $database SET count = count + 1 WHERE id = $id_select");

mysql_close($connect);


?>
