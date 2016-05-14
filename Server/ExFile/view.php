<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>이미지 업로드 공부</title>
</head>
<body>

<?
$connect=mysql_connect(  "52.79.139.48",  "ubuntu");
if(!$connect){
	die('Could not connect : '.mysql_error();
}
mysql_select_db( "footprint",$connect);

extract($_REQUEST);
$query= "select * from images where id=$id" ;
$result=mysql_query($query,$connect );
$row=mysql_fetch_array($result);

  	Header(  "Content-type:  image/jpeg");
  	echo $row[image];
mysql_close();
?>
</body>
</html>