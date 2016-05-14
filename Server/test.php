<?php

$file_name = $_FILES['upload_file']['name'];
$tmp_file = $_FILES['upload_file']["tmp_name"];

$file_path='/home/ubuntu/html/imageStorage/';

print_r($file_path);

$r = move_uploaded_file($tmp_file,$file_path);

echo 'asd';

print_r($r);

?>