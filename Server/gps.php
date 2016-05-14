 

<?php

header("Content-Type: text/html; charset=UTF-8")  //UTF-8 설정

?>

 

<?php 

 

//db연결

  mysql_connect("localhost", "footprint", "vntvmflsxm") or die (mysql_error());

  mysql_select_db("footprint");

 

// DB에서 데이터를 불러오기 위한 쿼리문 입니다. CASE문이 쓰여서 조금 어렵게 느껴지실 수도 있겠네요.

// CASE 문법에 대해서 살펴보시고 넘어가시기 바랍니다. 저는 MSSQL과 문법이 달라 조금 헤멨습니다.  

  $sql = "SELECT * FROM table1";

    $result = mysql_query( $sql ) or die (mysql_error());

 

 // 출력할 테이블 컬럼명 텍스트 입력

    echo "

    <html>

    <head><title>Written BY INNERGYAN</title></head>

    <body>

    <center>

    <H3>레코드</H3>

    <table width='1000' border='1'>

    <tr>

    <td width='5%' align='center'>id</td>

    <td width='20%' align='center'>writeDate</td>

    <td width='10%' align='center'>writeTime</td>

    <td width='25%' align='center'>article</td>

    <td width='15%' align='center'>filename</td>

    <td width='12%' align='center'>latitude</td>

     <td width='13%' align='center'>longitudeid</td>

     </tr>

";

 

 // 쿼리의 결과값이 있는 동안 반복을 통한 출력

    while($row = mysql_fetch_array($result))

    {

        echo("

        <tr>

        <td align='center'>$row[id]</td>

        <td align='center'>$row[name]</td>

        <td align='center'>$row[IN_Name]</td>

        <td align='center'>$row[IN_Mail]</td>

        <td align='center'>$row[filename]</td>

        <td align='center'>$row[IN_Radio]</td>

        <td align='center'>$row[IN_Combo]</td>

        </tr>

         ");



      }

 

 ?>
