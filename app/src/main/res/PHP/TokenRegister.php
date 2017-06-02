<?php
	
	if(isset($_POST["Token"])){
		$con = mysqli_connect("IP", "ID", "PW", "DB name", "Port");
		
		$con->query("set session character_set_connection=utf8;");
		$con->query("set session character_set_results=utf8;");
		$con->query("set session character_set_client=utf8;");
		
		$token = $_POST["Token"];
		$userID = $_POST["userID"];

		$query = "INSERT INTO TOKENS(Token, userID) Values ('$token', '$userID')";
		mysqli_query($con, $query);

		mysqli_close($con);
	}
?>