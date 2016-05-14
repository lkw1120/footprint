<? 
 ### 2001.12.11 
 ### imgSlidev2.php 
 ### copyleft to kindsome(kindsome@intizen.com, kindsome.net) 
 ### 사용하실 때 출처정도만이라도 밝혀 주세요 


$dir=opendir(".") ; 
 $count = 0 ; 
 while($file = readdir($dir)){ 
 $the_type = strrchr($file, "."); 
 $is_picture = eregi("jpg|gif|bmp|png",$the_type); 

 if($file != "." and $file != ".." and $is_picture) { 
 $slide[$count] = $file; 
 $count++; 
 } 
 } 
 closedir($dir); 

 ?> 

 <html> 
 <head> 
 <title> :: 이미지 갤러리 :: </title> 

 <STYLE type="text/css"> 

 .ver9 { color:; font-family:tahoma; font-size:9pt} 
 .input {border:solid 1 black; font-family:Tahoma; font-size:8pt; color:white; background-color:8c8c8c; width:80px; height:18px} 

 </STYLE> 

 <script language="ⓙavascript"> 
 <!-- 
<? 
 // php배열을 마지막 배열요소만 제외한 나머지를 ⓙavascript배열로 변환 
print(' var imagearray = new Array('); 
 for ($i=0; $i < ($count-1); $i++) { print("'" . $slide[$i] . "', "); } 

 // 마지막 배열요소 
print("'" . $slide[$count-1] . "'); "); 
 ?> 

 var i = 0; 
 var imgstring = new Array ; 
 var maximum = imagearray.length-1 ; 
 for (i=0; i < maximum; i++) { 
 imgstring[i] = new Image() ; 
 imgstring[i].src = imagearray[i] ; 
 } 


 //네비게이션 함수 시작 
function gallery(){ 
 if (i == 0){ 
 document.gallery.previous.value=" " ; 
 document.img.src = imagearray[i] ; 
 } 
 } 

 function previmg(){ 
 if (i != 0) { 
 i -- ; 
 document.img.src = imagearray[i] ; 
 document.gallery.next.value = "Next"; } 
 if (i == 0) { 
 document.gallery.previous.value=" " ; 
 } 
 } 

 function nextimg(){ 
 if (i != maximum ) { 
 i ++ ; 
 document.gallery.previous.value="Previous" ; 
 document.img.src = imagearray[i] ; 
 } 

 if (i == maximum){ 
 document.gallery.next.value=" "; 
 } 
 } 

 function firstimg(){ 
 i = 0 ; 
 document.img.src = imagearray[i] ; 
 document.gallery.previous.value=" "; 
 document.gallery.next.value="Next"; 
 } 

 function lastimg(){ 
 i = maximum ; 
 document.img.src = imagearray[i] ; 
 document.gallery.next.value=" " ; 
 document.gallery.previous.value="Previous" ; 
 } 




 --> 
 </script> 

 </head> 


 <body onLoad="gallery();" bgcolor="#ffffff" topmargin="10"> 
 <table width="100%" height="100% border="0" cellspacing="0" cellpadding="0"> 
 <tr> 
 <td align="center"> 

 <table width="675" border=1 bordercolorlight=Gainsboro bordercolordark=white cellpadding=3 bgcolor=f6f6f6> 
 <tr> 
 <td width="100%" height="660" align="center" valign="middle" > 
 <img src = "ⓙavascript:firstimg();" name="img" border=0> 
 </td> 
 </tr> 
 <tr> 
 <td height="40" align="center" valign="middle"> 

 <br> 
 <form name="gallery"> 
 <input type=button class="input" value="Previous" name="previous" onClick="previmg();"> 
 <input type=button class="input" value="First" name="first" onClick="firstimg();" > 
 <input type=button class="input" value="Last" name="last" onClick="lastimg();"> 
 <input type=button class="input" value="Next" name="next" onClick="nextimg();"> 
 </form> 

 </td> 
 </tr> 
 </table> 

 </td> 
 </tr> 
 </table> 