<html>
<head>
<title>MAMKA APP</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>

<div style="width:700px; margin:0 auto;">

<h3>Mamka App Upload</h3>   



<form action="" method="POST" enctype="multipart/form-data">
Date: <input type="date" id="start" name="date"
       value="2022-12-24"
       min="2022-12-12" max="2111-12-31" required>

<label>Text</label><br />
<textarea id="text" name="text" rows="4" cols="50" required>
</textarea>
<br />

Select image to upload: (max 15MB, will be downscaled...)
<input type="file" name="file_to_upload" id="file_to_upload" required/>
<br />
<input type="submit" value="Upload Image" name="submit">
</form>





<?php

if (isset($_POST['date']) && $_POST['date']!="") {

	echo "Uploading...";

	
	
	echo "<hr>".basename($_FILES["file_to_upload"]["name"])."<hr>";
	
	$uploadOk = 1;
	$target_dir = "./uploads/";
	$target_file = $target_dir . basename($_FILES["file_to_upload"]["name"]);
	$imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));

	
	//$target_file = $target_dir . basename($_FILES["file_to_upload"]["name"]);
	$target_file = $target_dir . $_REQUEST['date'] . "." . $imageFileType;  // change
	$to_resize_path = "api/uploads/".  $_REQUEST['date'] . "." . $imageFileType;
	$final_file_name = $_REQUEST['date'] . "." . $imageFileType; 

	// Check if image file is a actual image or fake image
	if(isset($_POST["submit"])) {
		$check = getimagesize($_FILES["file_to_upload"]["tmp_name"]);
		if($check !== false) {
			echo "File is an image - " . $check["mime"] . ".";
			$uploadOk = 1;
			} else {
				echo "File is not an image.";
			$uploadOk = 0;
			}
	}

	
	// Check if file already exists
	if (file_exists($target_file)) {
		echo "Sorry, file already exists.";
		$uploadOk = 0;
	}
	
	// Check file size
	if ($_FILES["file_to_upload"]["size"] > 15000000) {
		echo "Sorry, your file is too large.";
		$uploadOk = 0;
	}
	
	// Allow certain file formats
	if($imageFileType != "jpg"&& $imageFileType != "jpeg" ) {
		echo "Sorry, only JPG or JPEG files are allowed.";
		$uploadOk = 0;
	}
	
	// Check if $uploadOk is set to 0 by an error
	if ($uploadOk == 0) {
		echo "Sorry, your file was not uploaded.";
	// if everything is ok, try to upload file
	} else {
		echo "<br><br>name: ".$_FILES["file_to_upload"]["tmp_name"]."<br><br>";
		if (move_uploaded_file($_FILES["file_to_upload"]["tmp_name"], $target_file)) {
		echo "The file ". htmlspecialchars( basename( $_FILES["file_to_upload"]["name"])). " has been uploaded.";
		$newFileName = htmlspecialchars( basename( $_FILES["file_to_upload"]["name"]));
		} else {
		echo "Sorry, there was an error uploading your file.";
		}
	}


	echo "<br>Now downscale...";

	
	echo $to_resize_path;

	include("phpThumb-master/phpthumb.class.php");  


	//$image_destination = $target_file;
	//$image_destination = '../uploads/'.$_REQUEST['date'].".jpg";

	$phpThumb = new phpThumb();

	$phpThumb->setSourceFilename("../../".$to_resize_path);
	$phpThumb->setParameter('w', 1080);
	$phpThumb->setParameter('ar', 'x');
	
	//$phpThumb->setParameter('h', 100);      

	$phpThumb->GenerateThumbnail();
	if ($phpThumb->RenderToFile("../../".$to_resize_path)){
		echo "<br><strong>Render to file OK</strong>";
	}else{ 
		echo "<br><strong>Render to file FAIL</strong>";

		//Full	 debug:
		print_r( $phpThumb->debugmessages );
		//Just fatal errors:
		
		echo ($phpThumb->fatalerror);
	}

	echo "<br>End of downscaling..";
	echo "<br><img src=\"../".$to_resize_path."\">";


	include ("db.php");
	echo "<br>Entering into database. ".$_REQUEST['date']." , ".$_REQUEST['text']." - ".$final_file_name;
	$sql = "INSERT INTO `days_photos` (`date`, `text`, `file`) VALUES ('".$_REQUEST['date']."','".$_REQUEST['text']."', '".$final_file_name."');";
	echo $sql;
	if (mysqli_query($con, $sql)) {
		echo "<br>New record created successfully";
	} else {
		echo "<br>Error: " . $sql . "<br>" . $conn->error;
	}
	mysqli_close($con);
}

    ?>

</div>

</body>
</html>