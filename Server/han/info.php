<?
 function exifdate($imgfi){
  
   echo "---------------------------------------<br>";
  
//include"config.php";
$exif = @read_exif_data($imgfi); //@ <--표시가 핵심
$make = $exif[Make];//카메라 회사
$makemodel=$exif[Model];
$FileSize = $exif[FileSize]/(1024); 
$ccdsize = $exif[CCDWidth]; 
$size = substr($FileSize, 0, 5); 
$size2 = substr($ccdsize, 0, 5); 
$flash = $exif[FlashUsed]; 
echo(" 파일이름 : $exif[FileName]
<br>");
if(!$make){echo("");} 
else{echo" 제조회사 : $make
<br>";}
if(!$makemodel){echo("");}
else{echo" 제품명 : $makemodel
<br>";}
//else{echo" 제품명 : $exif[CameraModel]
 
if(!$exif[DateTime]){echo("");} 
else{echo" 촬영일자 : $exif[DateTime]
<br>";}
if(!$exif[Height] && $exif[Width]){echo("");} 
else{echo" 가로×세로 : $exif[Height] × $exif[Width]픽셀
<br>";}
echo(" 파일크기 : $size KB
<br>");
if($flash=="0"){echo(" 플래시 : 사용안함(Off)
<br>");} 
elseif($flash=="1"){echo(" 플래시 : 사용(On)
<br>");} 
elseif($flash==""){echo("");} 
else{echo("");}
if(!$exif[FocalLength]){echo("");} 
else{echo" 렌즈 작동 거리 : $exif[FocalLength]
<br>";}
if(!$exif[FocusDistance]){echo("");} 
else{echo" 촬영 거리 : $exif[FocusDistance]
<br>";}
if(!$exif[ApertureFNumber]){echo("");} 
else{echo" 조리개 : $exif[ApertureFNumber]
<br>";}
if(!$exif[ExposureTime]){echo("");} 
else{echo" 셔터속도 : $exif[ExposureTime]
<br>";}
if(!$exif[ISOspeed]){echo("");} 
else{echo" ISO 감도 : $exif[ISOspeed]
<br>";}
if(!$exif[ExifVersion]){echo("");} 
else{echo" EXIF 버전 : $exif[ExifVersion]
<br>";}
if(!$size2){echo("");} 
else{echo" CCD 크기 : $size2 mm(가로)
<br>";}
if(!$exif[Software]){echo("");} 
else{echo" 펌웨어 버전 : $exif[Software]
<br>";}
if(!$exif[Comments]){echo("");} 
else{echo" 설명 : $exif[Comments]
<br>";}

if($k=="Thumbnail"){ 
echo(" "); 
//$fp=fopen ("Thumbnail$adress",'a'); 
//fwrite ($fp, $v); 
//fclose ($fp); 
//echo "
//\n"; 
} 
}
?>
