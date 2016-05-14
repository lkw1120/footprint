<?php
   $map_width = 695;   // 지도의 폭 = 테이블 폭 - 5px
   $map_height = 395;  // 지도의 높이 = 테이블 높이 - 5px
   $map_key = "xxxxxxxxx";  // 네이버 지도api 키값
  $real_address = "서울시 구로구 구로3동 851"; // 주소
  $map_query = str_replace(" ","",$real_address); // 공백을 제거
 
   // 지도의 축적 1~11 사이의 자연수. 1에 가까울 수록 지도가 확대
  $map_zoom = 2;
  
   // euc-kr로 변환
  $map_cquery =iconv("utf-8","euc-kr","$map_query");
  
   // 여기부터 주소 검색 xml 파싱
  $pquery = "key=". $map_key. "&query=". $map_query;
   $fp = fsockopen ("maps.naver.com", 80, $errno, $errstr, 30);
   if (!$fp) {
     echo "$errstr ($errno)";
   } else {
     fputs($fp, "GET /api/geocode.php?");
     fputs($fp, $pquery);
     fputs($fp, " HTTP/1.1\r\n");
     fputs($fp, "Host: maps.naver.com\r\n");
     fputs($fp, "Connection: Close\r\n\r\n");
  
     $header = "";
     while (!feof($fp)) {
       $out = fgets ($fp,512);
       if (trim($out) == "") {
         break;
       }
       $header .= $out;
     }

     $mapbody = "";
     while (!feof($fp)) {
       $out = fgets ($fp,512);
       $mapbody .= $out;
     }
     
     $idx = strpos(strtolower($header), "transfer-encoding: chunked");
     
     if ($idx > -1) { // chunk data
       $temp = "";
       $offset = 0;
       do {
         $idx1 = strpos($mapbody, "\r\n", $offset);
         $chunkLength = hexdec(substr($mapbody, $offset, $idx1 - $offset));

         if ($chunkLength == 0) {
           break;
         } else {
           $temp .= substr($mapbody, $idx1+2, $chunkLength);
           $offset = $idx1 + $chunkLength + 4;
         }
       } while(true);
       $mapbody = $temp;
     }
     //header("Content-Type: text/xml; charset=utf-8");
     fclose ($fp);
   }     
   // 여기까지 주소 검색 xml 파싱


  // 여기부터 좌표값 변수에 등록
  $map_x_point_1=explode("<x>", $mapbody);
   $map_x_point_2=explode("</x>", $map_x_point_1[1]);
   $map_x_point=$map_x_point_2[0];

   $map_y_point_1=explode("<y>", $mapbody);
   $map_y_point_2=explode("</y>", $map_y_point_1[1]);
   $map_y_point=$map_y_point_2[0];
   // 여기까지 좌표값 변수에 등록
 
   echo "<script type='text/javascript' src='http://maps.naver.com/js/naverMap.naver?key=". $map_key ."'></script>";
 ?>

 <table border="0" cellpadding="0" cellspacing="0">
   <tr>
     <td style="padding:0 0 10px 0;"><img src="img/map_txt.jpg" alt="지도 이용방법" /></td>
   </tr>
   <tr>
     <td><table width="<?php echo $map_width; ?>" cellpadding="0" cellspacing="3" bgcolor="#f4f4f4">
       <tr>
         <td bgcolor="#ffffff"><table cellpadding="0" cellspacing="1" bgcolor="#cccccc" >
           <tr>
             <td bgcolor="#ffffff"><table cellpadding="3" cellspacing="1" bgcolor="#eeeeee">
               <tr>
                 <td bgcolor="#ffffff"><div id='mapContainer'></div></td>
               </tr>
             </table></td>
           </tr>
         </table></td>
       </tr>
     </table></td>
   </tr>
   <tr>
     <td align="right" style="padding:0 3px 5px 0;"><img src="img/map_openapi.gif" alt="네이버 OPENAPI" /></td>
   </tr>
 </table>
  
 <script type="text/javascript">
 <!--
  var x_point = <?php echo $map_x_point; ?>;
   var y_point = <?php echo $map_y_point; ?>;
   // 아이콘파일을 계정에 만드시고(지정된 위치에 표시되는 아이콘입니다) 이미지 주소 및 크기를 변경해주세요
  var icon = new NIcon("./images/map_icon.png", new NSize(33,53));
   var loc_Point = new NPoint(x_point,y_point); // 포인트 표시
  var map_mark = new NMark(loc_Point, icon ); // 지도에 아이콘 표시
  var mapObj = new NMap(document.getElementById('mapContainer'),<?php echo $map_width; ?>,<?php echo $map_height; ?>); // 지도창
  var infowin = new NInfoWindow();
   var zoom = new NZoomControl();
   var zoomlevel = <?php echo $map_zoom; ?>
  
   mapObj.addOverlay(map_mark); // 지도에 마크표시
  mapObj.setCenterAndZoom(loc_Point,zoomlevel); // 지도 중앙
  mapObj.addOverlay(infowin);
   zoom.setAlign("left"); // 줌 조절 버튼 왼쪽에 위치
  zoom.setValign("bottom"); // 줌 조절 버튼 아래에 위치
  mapObj.addControl(zoom);
 //-->
 </script>