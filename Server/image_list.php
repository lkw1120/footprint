<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8;" />
	<title>업로드 파일목록</title>
	<style>
		#file_list table{border:1px solid gray;border-collapse:collapse;};
		#file_list th, #file_list td{border:1px solid gray;padding:5px;}
	</style>
	<script>
		function image_view(image, width, height) {
			width = width + 10;
			hegiht = height + 10; 

			var op = "location=no, scrollbars=no, menubars=no, toolbars=no, resizeble=yes, left=0, top=0, width="+width+",height="+height;

			var url = "image_view.php?image="+image;

			popup = window.open(url,"ImageWindow", op);
			popup.focus();
		}//function

	</script>
</head>
<body>
<!--업로드 페이지로 이동링크-->
<p><a href='upload_form.htm'>업로드</a></p>

<?//PHP 5.2.5
//DB연결
$db = @new mysqli('localhost','id','password','dbname');
if(mysqli_connect_errno()) {
	exit("DB연결오류");
}

$result = $db->query("select * from file_list");

echo '<div id="file_list"><table>';
echo '<tr><th>no</th><th>파일명</th><th>저장파일명</th><th>타입</th><th>크기</th><th>넓이</th><th>높이</th><th>저장 IP</th><th>삭제</th></tr>';
while( $row = $result->fetch_assoc() ) {

	//이미지 정보 가져오기
	$name = $_SERVER['DOCUMENT_ROOT']."/uploads/".$row['db_filename'];
	$imagesize = getimagesize($name);

	print '<tr>';
	echo "<td>".htmlentities($row['no'])."</td>";
	//파일명 클릭시 새창으로 이미지 보여줌
	echo "<td><a href='javascript:image_view(\"{$row['db_filename']}\",{$imagesize[0]},{$imagesize[1]});'>".htmlspecialchars($row['upload_filename'])."</a></td>";
	//저장된 경로와 파일명을 연결해 출력
	echo "<td>".htmlentities($row['file_path'].$row['db_filename'])."</td>";
	echo "<td>".htmlentities($row['file_type'])."</td>";
	echo "<td>".htmlentities(number_format(round($row['file_size'])/1024))." kb</td>";
	echo "<td>".htmlentities($row['width'])."</td>";
	echo "<td>".htmlentities($row['height'])."</td>";
	echo "<td>".htmlentities($row['ip'])."</td>";
	//삭제 클릭시 이미지삭제 페이지로 이동
	echo "<td><a href=\"javascript:location.href='image_delete.php?image=".$row['db_filename']."'\">삭제</a></td>";
	print '</tr>';

}//while
print '</table></div>';
?>
</body>
</html>
