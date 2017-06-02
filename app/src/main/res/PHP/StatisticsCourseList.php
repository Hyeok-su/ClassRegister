<?php
	header("Content-Type: text/html; charset=UTF-8");

	$con = mysqli_connect("IP", "ID", "PW", "DB name", "Port");
	
	$con->query("set session character_set_connection=utf8;");
	$con->query("set session character_set_results=utf8;");
	$con->query("set session character_set_client=utf8;");
	
	$userID = $_GET["userID"];

	$result = mysqli_query($con, "SELECT COURSE.courseID, COURSE.courseGrade, COURSE.courseTitle, COURSE.coursePersonnel, COUNT(SCHEDULE.courseID), COURSE.courseCredit FROM SCHEDULE, COURSE WHERE SCHEDULE.courseID IN(SELECT SCHEDULE.courseID FROM SCHEDULE WHERE SCHEDULE.userID = '$userID') AND SCHEDULE.courseID = COURSE.courseID GROUP BY SCHEDULE.courseID");
	
	$response = array();
	while($row = mysqli_fetch_array($result)) {
		array_push($response, array("courseID"=>$row[0], "courseGrade"=>$row[1], "courseTitle"=>$row[2], "coursePersonnel"=>$row[3], "COUNT(SCHEDULE.courseID)"=>$row[4], "courseCredit"=>$row[5]));
	}
	
	echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
	mysqli_close($con);
?>