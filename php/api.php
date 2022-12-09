<?php
header("Content-Type:application/json");

include('db.php');



if ($_GET['do'] == "show_one"){

	if ($_GET['date']!="") {

		$photo_date = $_GET['date'];
		$result = mysqli_query($con,"SELECT * FROM `days_photos` WHERE date = '$photo_date'");
		if(mysqli_num_rows($result)>0){
		$row = mysqli_fetch_array($result);
		$id = $row['id'];
		$date = $row['date'];
		$file = $row['file'];
		$text = $row['text'];
		response($id, $date, $file,$text);
		mysqli_close($con);
		}else{
			response(NULL, NULL, 200,"No Record Found");
			}
	}else{
		response(NULL, NULL, 400,"Invalid Request");
		}
	

}elseif($_GET['do'] == "show_list"){

	if ($_GET['from_date'] == ""){
		$result = mysqli_query($con, "SELECT * FROM `days_photos`");
	}else{
		// from_date is set, need to only display pictures today and older.. not future photos
		
		$result = mysqli_query($con, "SELECT * FROM `days_photos` WHERE date <= '".$_GET['from_date']."'");
	}

	
	$emparray = array();
	while($row = mysqli_fetch_assoc($result))
	{
		$emparray[] = $row;
	}
	echo json_encode($emparray);
	mysqli_close($con); //close the db connection

}else{
	response(NULL, NULL, 400,"Invalid Request");
}

function response($id,$date,$file,$text){
	$response['id'] = $id;
	$response['date'] = $date;
	$response['file'] = $file;
	$response['text'] = $text;
	
	$json_response = json_encode($response);
	echo $json_response;
}
?>