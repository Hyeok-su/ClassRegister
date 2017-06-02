<?php
	header("Content-Type: text/html; charset=UTF-8");
	
	$con = mysqli_connect("IP", "ID", "PW", "DB name", "Port");
	
	$con->query("set session character_set_connection=utf8;");
	$con->query("set session character_set_results=utf8;");
	$con->query("set session character_set_client=utf8;");
	
	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];
	$userGrade = $_POST["userGrade"];
	$userMajor = $_POST["userMajor"];
	$userEmail = $_POST["userEmail"];
	
	$checkedPassword = password_hash($userPassword, PASSWORD_DEFAULT);
	$statement = mysqli_prepare($con, "INSERT INTO USERS VALUES (?, ?, ?, ?, ?)");
	mysqli_stmt_bind_param($statement, "sssss", $userID, $checkedPassword, $userGrade, $userMajor, $userEmail);
	mysqli_stmt_execute($statement);
	
	$response = array();
	$response["success"] = true;
	
	echo json_encode($response);
	
	mysqli_close($con);
?>