<?php
	header("Content-Type: text/html; charset=UTF-8");
	
	$con = mysqli_connect("IP", "ID", "PW", "DB name", "Port");
	
	$con->query("set session character_set_connection=utf8;");
	$con->query("set session character_set_results=utf8;");
	$con->query("set session character_set_client=utf8;");
	
	$courseUniversity = $_GET["courseUniversity"];
	$courseYear = $_GET["courseYear"];
	$courseTerm = $_GET["courseTerm"];
	$courseArea = $_GET["courseArea"];
	$courseMajor = $_GET["courseMajor"];
	
	$result = mysqli_query($con, "SELECT * FROM COURSE WHERE courseUniversity = '$courseUniversity' AND courseYear = '$courseYear' AND courseTerm = '$courseTerm' AND courseArea = '$courseArea' AND courseMajor = '$courseMajor'");

	$response = array();
	
	while($row = mysqli_fetch_array($result)) {
		array_push($response, array("courseID"=>$row[0], "courseUniversity"=>$row[1], "courseYear"=>$row[2], "courseTerm"=>$row[3], "courseArea"=>$row[4], "courseMajor"=>$row[5], "courseGrade"=>$row[6], "courseTitle"=>$row[7], "courseCredit"=>$row[8], "coursePersonnel"=>$row[9], "courseProfessor"=>$row[10], "courseTime"=>$row[11], "courseRoom"=>$row[12]));
	}
	
	echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
	mysqli_close($con);
?>