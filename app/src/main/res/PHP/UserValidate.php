<?php
	header("Content-Type: text/html; charset=UTF-8");
	
	$con = mysqli_connect("IP", "ID", "PW", "DB name", "Port");
	
	$con->query("set session character_set_connection=utf8;");
	$con->query("set session character_set_results=utf8;");
	$con->query("set session character_set_client=utf8;");
	
	$userID = $_POST["userID"];
	
	$statement = mysqli_prepare($con, "SELECT * FROM USERS WHERE userID = ?");
	mysqli_stmt_bind_param($statement, "s", $userID);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userID);
	
	$response = array();
	$response["success"] = true;
	
	while(mysqli_stmt_fetch($statement)) {
		$response["success"] = false;
		$response["userID"] = $userID;
	}
	
	echo json_encode($response);
	
	mysqli_close($con);
?>