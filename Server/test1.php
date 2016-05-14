<html>
<link rel="stylesheet" href="css/gallery.css">
<head>
	<title>asdasdasd</title>
	<meta http-equiv="Content-Type" content="text/html; cjarset=iso-8859-1">
</head>

<body>

<?php

  mysql_connect("localhost", "footprint", "vntvmflsxm");

  mysql_select_db("footprint");


  $sql = mysql_query("select *from footprint");




  echo "<table>";

 


  while($row=mysql_fetch_array($sql))
  {
   
  
  	echo "<tr>";
  	echo "<td>";

    $a = $row[filename]; 
    echo "$a";




     ?>
 <div class = "gallery-item">
    <img src="<?php echo "imageStorage/$a";?>" height="200" width="200"> 

</div>



<?php 

 echo "</td>";
  	echo "<td>"; echo $row["id"];  ; echo "</td>";
  	echo "</tr>";
   

  }

echo "</table>";

?>




</body>
</html>
