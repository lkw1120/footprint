
<?php
if(isset($_FILES['file'])){
$file_name = $_FILES['file']['name'];
$tmp_name = $_FILES['file']["tmp_name"];
$local_image = "imageStorage/";
$r = move_uploaded_file($tmp_name,$local_image.$name_file);

print_r($r);

}




?>


<form method="post" enctype="multipart">

<input type="file" name="file" /><br/><br/>

<input type="submit" name="submit" value="upload"/>

</form>