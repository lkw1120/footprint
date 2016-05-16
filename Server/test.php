<?php

$file_name = $_FILES['upload_file']['name'];
print_r($file_path);
$tmp_file = $_FILES['upload_file']["tmp_name"];
print_r($tmp_file);

$file_path = './files/'.$file_name;




print_r($file_path);

$r = move_uploaded_file($tmp_file,$file_path);

print_r($r);

?>

