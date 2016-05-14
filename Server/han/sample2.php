<!DOCTYPE html>
<html>
<head>

	<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
	<meta charset="utf8">
	<title>구글맵 API 활용하기</title>
	<script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
	

<?php
//계정 정보 
$mysql_host = 'localhost';
$mysql_user = 'footprint';
$mysql_password ='vntvmflsxm';
$mysql_db = 'footprint';

//접속 
$connect=mysql_connect(  $mysql_host, $mysql_user, $mysql_password);
$dbconn = mysql_select_db($mysql_db, $connect);
//charset 설정
mysql_set_charset(utf8);
//쿼리
$lat = "select id, latitude, longitude from $mysql_db";
//$lng = "select longitude from $mysql_db";

$result1 = mysql_query($lat, $connect);
//$result2 = mysql_query($lng, $connect);
$row = mysql_fetch_array($result1);
//$col = mysql_fetch_array($result2);
//foreach($row as $value){
foreach ($row as $value) {
	echo "$value<br>";
}
echo $row['id'];
echo ", ";
echo $row['latitude'];
echo ", ";
echo $row['longitude'];
//echo $col['longitude'];
//}

mysql_close();

?>
	<script>
	var lat=<?=$row['latitude']?>;
	var lng=<?=$row['longitude']?>;
		function initialize() {

			/*
				http://openapi.map.naver.com/api/geocode.php?key=f32441ebcd3cc9de474f8081df1e54e3&encoding=euc-kr&coord=LatLng&query=서울특별시 강남구 강남대로 456
                위의 링크에서 뒤에 주소를 적으면 x,y 값을 구할수 있습니다.
			*/
			//var lat;		= 37.451128;		// Y 좌표
			//var lng;		= 126.656815;		// X 좌표

			var zoomLevel		= 16;						// 지도의 확대 레벨 : 숫자가 클수록 확대정도가 큼

			var markerTitle		= "인하대학교";				// 현재 위치 마커에 마우스를 오버을때 나타나는 정보
			var markerMaxWidth	= 300;						// 마커를 클릭했을때 나타나는 말풍선의 최대 크기

			// 말풍선 내용
			var contentString	= '<div>' +
			'<h2>인하대</h2>'+
			'<p>이강원, 김재간, 한주환<br />' +
            '캡스토오오오옹ㅇ오오오.</p>' +
			//'<a href="http://www.daegu.go.kr" target="_blank">http://www.daegu.go.kr</a>'+ //링크도 넣을 수 있음
			'</div>';

			var myLatlng = new google.maps.LatLng(lat, lng);
			var mapOptions = {
								zoom: zoomLevel,
								center: myLatlng,
								mapTypeId: google.maps.MapTypeId.ROADMAP
			}
			var map = new google.maps.Map(document.getElementById('map_view'), mapOptions);

			var marker = new google.maps.Marker({
													position: myLatlng,
													map: map,
													title: markerTitle
			});

			var infowindow = new google.maps.InfoWindow(
														{
															content: contentString,
															maxWidth: markerMaxWidth
														}
			);

			google.maps.event.addListener(marker, 'click', function() {
				infowindow.open(map, marker);
			});
		}
	</script>
</head>

<body onload="initialize()">
	<div id="map_view" style="width:500px; height:300px;"></div>
</body>
</html>