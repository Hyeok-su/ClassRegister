<?php
	header("Content-Type: text/html; charset=UTF-8");
	
	$con = mysqli_connect("IP", "ID", "PW", "DB name", "Port");
	
	$con->query("set session character_set_connection=utf8;");
	$con->query("set session character_set_results=utf8;");
	$con->query("set session character_set_client=utf8;");
	
	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];
		
	$statement = mysqli_prepare($con, "SELECT * FROM USERS WHERE userID = ?");
	mysqli_stmt_bind_param($statement, "s", $userID);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userID, $checkedPassword, $userGrade, $userMajor, $userEmail);
	
	$response = array();
	$response["success"] = false;
	
	while(mysqli_stmt_fetch($statement)) {
		if(password_verify($userPassword, $checkedPassword)) {
			$response["success"] = true;
			$response["userID"] = $userID;
		}
	}
	
	echo json_encode($response);
	
	mysqli_close($con);
?>