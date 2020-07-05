<?php
	require_once "a_star.php";
	require_once "../koneksi/koneksi.php";
error_reporting(E_ALL & ~E_NOTICE);

	$database = new koneksi();
	$db=$database->getConnection();

	$id = $_POST['id'];

       $mylat = $_POST['mylat'];
       $mylng = $_POST['mylng'];
 //$id = "66";
      // iki seng di gedung af
    //  $mylat = "-7.946617";
      // $mylng = "112.615236";

      
      //$mylat = "-7.985328";
      // $mylng = "112.646185";
      //echo "<pre>";
      //var_dump($_GET);

	$query = $db->prepare('SELECT latitude, longitude from faskes where id = :id');
        $query->bindParam(':id', $id);
        $query->execute();
        $q = $query->fetchAll(PDO::FETCH_ASSOC);
        foreach ($q as $key => $value) {
    	     $lat = $value['latitude'];
             $lng = $value['longitude'];
        }

//echo $lat;
//echo $lng;


	//Lokasi File JSON
	$fileLocation = "../file/graph";
	
	//Moco File JSON
	$jsonGraph = File::Readfile($fileLocation);

	//Gawe Graph
	$graph = new Graph($jsonGraph);
	
	//Implementasi Graph nang Algoritma
	$algorithm = new A_Star($graph);
	
	//Koordinat awal
	$start = new LatLng($mylat, $mylng);
	
	//Koordinat tujuan
	$destination = new LatLng($lat, $lng);

	//Hasil e
	$result = $algorithm->getPaths($start, $destination);
	
	//Convert hasile nang JSON
	/*
		lek pengen ngerti struktur object hasil e, buka o http://www.jsoneditoronline.org/
		terus paste JSON ne nang kono
	*/

if ($result->distance < 1){
$result->distance = (number_format($result->distance, 3) * 1000)." m";
}else if ($result->distance < 100){
$result->distance = number_format($result->distance, 1)." km";
}else{
$result->distance = number_format($result->distance, 0)." km";
}

	echo json_encode($result);