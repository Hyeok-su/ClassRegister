<?php
	header("Content-Type: text/html; charset=UTF-8");

	$con = mysqli_connect("IP", "ID", "PW", "DB name", "Port");
	
	$con->query("set session character_set_connection=utf8;");
	$con->query("set session character_set_results=utf8;");
	$con->query("set session character_set_client=utf8;");
	
	$userID = $_GET["userID"];
	
	$result = mysqli_query($con, "SELECT COURSE.courseID, COURSE.courseTime, COURSE.courseProfessor, COURSE.courseTitle, COURSE.courseCredit FROM USERS, COURSE, SCHEDULE WHERE USERS.userID = '$userID' AND USERS.userID = SCHEDULE.userID AND SCHEDULE.courseID = COURSE.courseID");
	
	$response = array();
	while($row = mysqli_fetch_array($result)) {
		array_push($response, array("courseID"=>$row[0], "courseTime"=>$row[1], "courseProfessor"=>$row[2], "courseTitle"=>$row[3], "courseCredit"=>$row[4]));
	}
	
	echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
	mysqli_close($con);
?>