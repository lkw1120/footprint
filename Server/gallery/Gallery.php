

<?php



$connect = mysql_connect('localhost','footprint','vntvmflsxm') or die("Failed");

$db = mysql_select_db('footprint', $connect);

$sql = "INSERT INTO image VALUES";

$sql = $sql."($image)";

mysql_query($sql, $connect);

class Gallery{
public $path; 

	public function __construct(){
		$this->path = __DIR__ . '\images';
		}


	public function setPath($path){
	if(substr($path,-1)=== '/'){
		$path = substr($path,0,-1);
}
	 $this->path=$path;
	}


	private function getDirectory($path){
		return scandir($path);
	}


public function getImages($extensions = array('jpg','png')){
		$images = $this->getDirectory($this->path);




foreach($images as $index => $image){
   

		$extension = strtolower(end(explode('.',$image)));

		if(!in_array($extension, $extensions)){
			unset($images[$index]);


} else {

  
	$images[$index] = array(
				'full' => $this->path .'/'. $image,
				'thumb' => $this->path .'/thumbs/' . $image
															);
  
$que = "select * from image";



$result = mysql_query($que);


$row=mysql_fetch_assoc($result);

echo '<pre>' , print_r($row), '</pre>';



  
$exif = @exif_read_data($image, 0, true);




/*

if ($exif) {
    $gps_lat = null;
    $gps_lon = null;
    $gps_ele = null;

    if ($exif["GPS"]) { //GPS 정보가 있다면

        if ($exif["GPS"]["GPSLatitude"] && $exif["GPS"]["GPSLongitude"]) { //위경도 좌표가 있다면

            list($temp_d1, $temp_d2) = sscanf($exif["GPS"]["GPSLatitude"][0], "%d/%d"); //문자->숫자로 계산

            $gps_lat_d = $temp_d1/$temp_d2;
            list($temp_d1, $temp_d2) = sscanf($exif["GPS"]["GPSLatitude"][1], "%d/%d");
            $gps_lat_m = $temp_d1/$temp_d2;
            list($temp_d1, $temp_d2) = sscanf($exif["GPS"]["GPSLatitude"][2], "%d/%d");
            $gps_lat_s = $temp_d1/$temp_d2;

            
            list($temp_d1, $temp_d2) = sscanf($exif["GPS"]["GPSLongitude"][0], "%d/%d"); //문자->숫자로 계산
            $gps_lon_d = $temp_d1/$temp_d2;
            list($temp_d1, $temp_d2) = sscanf($exif["GPS"]["GPSLongitude"][1], "%d/%d");
            $gps_lon_m = $temp_d1/$temp_d2;
            list($temp_d1, $temp_d2) = sscanf($exif["GPS"]["GPSLongitude"][2], "%d/%d");
            $gps_lon_s = $temp_d1/$temp_d2;

            
            $gps_lat = $gps_lat_d+$gps_lat_m/60+$gps_lat_s/3600; //도분초를 도로 변환
            $gps_lon = $gps_lon_d+$gps_lon_m/60+$gps_lon_s/3600;

            
            list($temp_d1, $temp_d2) = sscanf($exif["GPS"]["GPSAltitude"], "%d/%d"); //문자->숫자로 계산
            $gps_ele = $temp_d1/$temp_d2;

            
            echo "GPS lat d, m, s : $gps_lat_d, $gps_lat_m, $gps_lat_s<br />";
            echo "GPS lon d, m, s : $gps_lon_d, $gps_lon_m, $gps_lon_s<br />";

            //echo "GPS lat, lon, ele : $gps_lat, $gps_lon, $gps_ele<br />";

        }


    }


}
 
*/


}
}
	return (count($images)) ? $images :false;

	}






}





?>
