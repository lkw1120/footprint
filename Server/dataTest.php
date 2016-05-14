<!--
Content-Type 이 application/x-www-form-urlencoded 인 경우에는
$_POST[postData] 로 받아서 처리하면 되는데,
Content-Type 자체가 application/json 인 경우에는
php 에서 다음과 같이 처리한다.
http://gangslab.tistory.com/entry/PHP%EC%97%90%EC%84%9C-json-post-%EB%A5%BC-%EC%B2%98%EB%A6%AC%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95
(주소 참조)
-->

<?php

$mysql_hostname = "localhost";
$mysql_username = "footprint";
$mysql_password = "vntvmflsxm";
$mysql_database = "footprint";

mysql_connect($mysql_hostname, $mysql_username, $mysql_password);
mysql_select_db($mysql_database);
mysql_set_charset("utf8");

 mysql_query("INSERT INTO table1 (name , images1)
              VALUES ('8','asd.jpg');");


//if(!empty(@$_POST["id"])) {


  $date = @$_POST["date"];
  $time = @$_POST["time"];
  $article = @$_POST["article"];;
  $filename = @$_POST["filename"];
  $latitude = @$_POST["latitude"];
  $longitude = @$_POST["longitude"];


  $file_path = "/home/ubuntu/html/imageStorage/";
  $file_path = $file_path . basename($_FILES["filename"]["name"]);



    if(move_uploaded_file($_FILES["filename"]["tmp_name"], $file_path)) {

        echo "success";
    } else{
        echo "fail";
    }


  mysql_query("INSERT INTO footprint (writeDate, writeTime, article, filename, latitude, longitude)
              VALUES ('$date', '$time', '$article','$filename', '$latitude', '$longitude');");
/*}
    else{
echo "false";

    }
*/

print_r($date);

echo "asd";

mysql_close($connect);

?>
