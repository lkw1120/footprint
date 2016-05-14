
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
mysql_set_charset(utf8);
$id_select = @$_POST["id"];


// Select all the rows in the markers table
$query = "SELECT * FROM footprint WHERE id = '$id_select'";
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
  echo '' . parseToXML($row['id']) . '';  //id
  echo ',';
  echo '' . $row['writeDate'] . '';  //writeDate
  echo ',';
  echo '' . $row['writeTime'] . '';  //writeTime
  echo ',';
  echo '' . $row['article']. ''; //article
  echo ',';
  echo '' . $row['filename'] .''; //name
  echo ',';
  echo '' . $row['latitude'] . ''; //lat
  echo ',';
  echo '' . $row['longitude'] . '';  //lng
  echo ',';

}

// End XML file
//echo '</markers>';

?>
