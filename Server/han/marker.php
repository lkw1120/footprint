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

// Select all the rows in the markers table
$query = "SELECT * FROM footprint WHERE 1";
$result = mysql_query($query);
if (!$result) {
  die('Invalid query: ' . mysql_error());
}

header("Content-type: text/xml");

// Start XML file, echo parent node
echo '<markers>';

// Iterate through the rows, printing XML nodes for each
while ($row = @mysql_fetch_assoc($result)){
  // ADD TO XML DOCUMENT NODE
  echo '<marker ';
  echo 'id="' . parseToXML($row['id']) . '" ';
  echo 'lat="' . $row['latitude'] . '" ';
  echo 'lng="' . $row['longitude'] . '" ';
  echo 'writeDate="' . $row['writeDate'] . '" ';
  echo 'writeTime="' . $row['writeTime'] . '" ';
echo '/>';
}

// End XML file
echo '</markers>';

/*
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
*/
?>