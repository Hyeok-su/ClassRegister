<?php 
	
	function send_notification ($tokens, $message) {
		$url = 'https://fcm.googleapis.com/fcm/send';
		$fields = array(
			 'registration_ids' => $tokens,
			 'data' => $message
			);

		$headers = array(
			'Authorization:key = your Key',
			'Content-Type: application/json'
			);

	   $ch = curl_init();
       curl_setopt($ch, CURLOPT_URL, $url);
       curl_setopt($ch, CURLOPT_POST, true);
       curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
       curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
       curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);  
       curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
       curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
       $result = curl_exec($ch);           
       if ($result === FALSE) {
           die('Curl failed: ' . curl_error($ch));
       }
       curl_close($ch);
       return $result;
	}
	
	//데이터베이스에 접속해서 토큰들을 가져와서 FCM에 발신요청
	$con = mysqli_connect("IP", "ID", "PW", "DB name", "Port");
		
	$con->query("set session character_set_connection=utf8;");
	$con->query("set session character_set_results=utf8;");
	$con->query("set session character_set_client=utf8;");
		
	$userID = $_POST['userID'];
	$courseTitle = $_POST['courseTitle'];
	$courseID = $_POST['courseID'];
	
	$sql = "Select Token From DIPS, TOKENS WHERE DIPS.courseID = '$courseID' AND DIPS.userID = TOKENS.userID";
	
	$result = mysqli_query($con,$sql);
	$tokens = array();

	if(mysqli_num_rows($result) > 0 ){

		while ($row = mysqli_fetch_assoc($result)) {
			$tokens[] = $row["Token"];
		}
	}

	$myMessage = $courseTitle. '에서 결원이 발생하였습니다.';;
	
	if ($myMessage == ""){
		$myMessage = "신청한 강의 중 결원이 발생하였습니다.";
	}

	$message = array("message" => $myMessage);
	$message_status = send_notification($tokens, $message);
	echo $message_status;
	
	mysqli_close($con);
 ?>