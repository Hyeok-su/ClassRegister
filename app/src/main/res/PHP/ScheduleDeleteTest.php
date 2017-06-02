<?php
	header("Content-Type: text/html; charset=UTF-8");

	$con = mysqli_connect("localhost", "okpo0356", "1356rnfl", "okpo0356");
	
	$con->query("set session character_set_connection=utf8;");
	$con->query("set session character_set_results=utf8;");
	$con->query("set session character_set_client=utf8;");
	
	$userID = $_POST["userID"];
	$courseID = $_POST["courseID"];
	
	$statement = mysqli_prepare($con, "DELETE FROM SCHEDULE WHERE userID = '$userID' AND courseID = '$courseID'");
	mysqli_stmt_bind_param($statement, "si", $userID, $courseID);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;
	
	echo json_encode($response);
?>