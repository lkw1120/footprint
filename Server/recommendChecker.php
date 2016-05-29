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

  $info = mysql_query("SELECT footprint FROM footprint
              WHERE writeDate = '$date' AND writeTime = '$time';");
  $info = mysql_fetch_row($info);
  $filename= $info[4];
  $checker = mysql_query("SELECT recommendChecker FROM recommendChecker
              WHERE ipNum = $key AND fp_id = $filename;");
if($checker==null) {
    mysql_query("UPDATE footprint SET count = count + 1 WHERE id = $filename");

    mysql_query("INSERT INTO recommendChecker (fp_id, ipNum)
                VALUES ('$filename', '$key');");
    echo "SUCCESS";
}else {
    echo "EXIST";
}
mysql_close($connect);

?>
