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


$query = "SELECT * FROM table1 WHERE 1";
$result = mysql_query($query);
if(!$result) {
  die('Invalid query: ' . mysql_error());
}

header("Content-type: text/xml");

echo '<images>';

while($row = @mysql_fetch_assoc($result)){
  echo '<image ';
  echo 'name="' . parseToXML($row['name']) . '" ';
  echo 'images1="' . $row['images1'] . '" ';
  echo '/>';
}

echo '</images>';

?>