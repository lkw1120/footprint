// 위도 경도 거리 참조 링크, http://blog.daum.net/suchil/16300830 
// php 연산함수, http://www.php.net/manual/kr/book.math.php 
// 위도 1도 111Km  = 111,000 m 
// 경도 1도  88.8km =  88,800 m 
// latitude (lat.) 위도 
// longitude (long.) 경도 
// 총거리 = 루트 √( 경도거리제곱+위도거리제곱 ) 

// 위도미터거리 경도미터거리 ( 정확한 수치는 모름, 안 가봐서 ^^;) 
$MeterByDegree = Array('lat'=> 111000, 'lon' => 88800 ); 

// 입력: 위도,경도 출력:배열(위도미터값,경도미터값) 
function getMeter( $gpsNow, $gpsOther, $show=0 ) { 
global $MeterByDegree; 

//print_r($gpsNow); 
//print_r($gpsOther); 
$latDist = abs($gpsNow[1] - $gpsOther[1]); // 위도거리 
$lonDist = abs($gpsNow[2] - $gpsOther[2]); // 경도거리 
echo '위도거리 = ' . number_format($latDist, 10) . "<br>\n"; 
echo '경도거리 = ' . number_format($lonDist, 10) . "<br>\n"; 
$latMeter = $latDist * $MeterByDegree['lat']; // 위도미터 
$lonMeter = $lonDist * $MeterByDegree['lon']; // 경도미터 
echo '위도미터 = ' . number_format($latMeter, 10) . "<br>\n"; 
echo '경도미터 = ' . number_format($lonMeter, 10) . "<br>\n"; 
// 루트 ( 제곱 + 제곱 ) 
$DistMeter = sqrt( $latMeter*$latMeter +  $lonMeter*$lonMeter ); 

if($show=='show') showMeter( $gpsNow, $gpsOther, $DistMeter ); 
return $DistMeter;  
} 

function showMeter( $gpsNow, $gpsOther, $DistMeter ) { 
$DistKm = $DistMeter / 1000; 
$DistKmStr = number_format( $DistKm ); 
$DistMeterStr = number_format( $DistMeter ); 

 	$str  = "\n"; 
$str .= $gpsNow[0] . '와 ' . $gpsOther[0] . " 의 거리~는 "; 
$str .= $DistMeter . ' m (<B>' . $DistKm . "</B> km) 입니다.<br>\n"; 
$str .= $DistMeterStr . ' m (<B>' . $DistKmStr . "</B> km) 입니다.<br>\n"; 
$str .= $gpsNow[0] . ' => (' . $gpsNow[3] . ")<br>\n"; 
$str .= $gpsOther[0] . ' => (' . $gpsOther[3] . ")<br>\n"; 
if( $DistMeter > 30000 ) 
$str .= "<font color=red> 30 킬로~가 넘습니다.</font><br>\n"; 
else 
$str .= "<font color=red> 30 킬로~이내의 거리에 있습니다.</font><br>\n"; 
$str .= '<hr>'; 
echo $str; 
} 