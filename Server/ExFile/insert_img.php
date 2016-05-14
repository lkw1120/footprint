<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>이미지 업로드 공부</title>
</head>
<body>

<form action='image_up.php' method='POST' enctype='multipart/form-data'>
<input TYPE=hidden name=mode value=insert>
<table>
<tr> <td>올릴 이미지:</td>
<td><input type='file' name='image'></td></tr>
<tr> <td>제목</td>
<td><input type='text' name='title'></td></tr>
<tr> <td colspan = 2>
<input type='submit' value='이미지 전송 '></td></tr>
</table>
</form>


</body>
</html>
