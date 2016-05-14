<?php

echo "<h3> mysql_connect</h3>";
echo "<hr>";

$good = mysql_connect('52.79.139.48:22','footprint','vntvmflsxm');

if(#good)
{
	echo "mysql 서버 연결<br>";
	echo $good;
	echo "<hr>";

}
else
{
	echo "<hr>";
	echo "...노접속";


}

mysql_close($good);
?>