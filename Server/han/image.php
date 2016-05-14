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

$query2 = "SELECT * FROM table1 WHERE 1";
$result2 = mysql_query($query2);
if(!$result2) {
  die('Invalid query: ' . mysql_error());
}

header("Content-type: text/xml");

echo '<images>';

while($row2 = @mysql_fetch_assoc($result2)){
  echo '<image ';
  echo 'name=" ' . paresToXML($row2['name']) . '" ';
  echo 'images1' . $row2['images1'] . '" ';
  echo '/>';
}

echo '</images>';

?>