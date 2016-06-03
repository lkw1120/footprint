<?php

$mysql_hostname = "localhost";
$mysql_username = "footprint";
$mysql_password = "vntvmflsxm";
$mysql_database = "footprint";

mysql_connect($mysql_hostname, $mysql_username, $mysql_password);
mysql_select_db($mysql_database);
mysql_set_charset("utf8");



  $key = @$_POST["key"];
  $date = @$_POST["date"];
  $time = @$_POST["time"];
  $article = @$_POST["article"];

$id = mysql_query("SELECT footprint.id FROM footprint WHERE writeDate = '$date' AND writeTime = '$time';");
$id = mysql_result($id,0);
$checker = mysql_query("SELECT recommendChecker.ipNum FROM recommendChecker WHERE fp_id = '$id';");
if(checker!=NULL) {
  while($row = mysql_fetch_array($checker)){
    $fp_id[] = $row["ipNum"];
  }
  if (in_array($key, $fp_id)) {
      echo "EXIST";
  }
  else {
    mysql_query("UPDATE footprint SET count = count + 1 WHERE writeDate = '$date' AND writeTime = '$time';");
    mysql_query("INSERT INTO recommendChecker (fp_id, ipNum) VALUES ('$id','$key');");
    echo "SUCCESS";
  }
}



mysql_close($connect);

?>
