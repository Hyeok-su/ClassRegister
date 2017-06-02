<?php
	header("Content-Type: text/html; charset=UTF-8");

	$con = mysqli_connect("IP", "ID", "PW", "DB name", "Port");
	
	$con->query("set session character_set_connection=utf8;");
	$con->query("set session character_set_results=utf8;");
	$con->query("set session character_set_client=utf8;");
	
	$userID = $_GET["userID"];
	
	$result = mysqli_query($con, "SELECT NOTICE.noticeContent, NOTICE.noticeName, NOTICE.noticeDate FROM NOTICE, USERS WHERE NOTICE.noticeMajor = USERS.userMajor AND USERS.userID = '$userID' ORDER BY noticeDate DESC;");
	$response = array();

	while($row = mysqli_fetch_array($result)) {
		array_push($response, array("noticeContent"=>$row[0], "noticeName"=>$row[1], "noticeDate"=>$row[2]));
	}
	
	echo json_encode(array("response"=>$response));
	mysqli_close($con);
?>