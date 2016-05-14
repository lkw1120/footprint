<?php

 

$connect = mysql_connect('localhost','footprint','vntvmflsxm') or die("Failed");;

$db = mysql_select_db('footprint', $connect);



if($db)
{
 echo "ssss";

}

$sql = "SHOW TABLES FROM image";
$result = mysql_query($sql);
/*
$sql = "INSERT INTO image VALUES";
$sql = $sql."('3.PNG')";
mysql_query($sql, $connect);

$sql = "INSERT INTO image VALUES";
$sql = $sql."('4.PNG')";
mysql_query($sql, $connect);
*/
print_r($result);


?>
