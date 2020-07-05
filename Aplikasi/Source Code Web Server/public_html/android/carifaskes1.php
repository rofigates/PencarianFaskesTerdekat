<?php

require_once '../koneksi/koneksi.php';

$database = new koneksi();
$db=$database->getConnection();
error_reporting(E_ALL & ~E_NOTICE);

$jenis = $_POST['jenis'];
$kecamatan = $_POST['kecamatan'];
$whereClosure = false;

$querys = "SELECT * FROM faskes";

if ($kecamatan != "Semua Kecamatan"){
	$querys .= " WHERE kecamatan = :kecamatan";
	$whereClosure = true;
}

if ($jenis != "Semua Fasilitas Kesehatan"){
	if (!$whereClosure){
		$querys .= " WHERE ";
	}else{
		$querys .= " AND ";
	}
	$querys .= "jenis = :jenis";
}
//echo $querys;
$query = $db->prepare($querys);
if ($kecamatan != "Semua Kecamatan"){
	$query->bindParam(':kecamatan',$kecamatan);
}
if ($jenis != "Semua Fasilitas Kesehatan"){
	$query->bindParam(':jenis',$jenis);
}
$query->execute();
$q = $query->fetchAll(PDO::FETCH_ASSOC);
$data = "{fk1:".json_encode($q)."}";
print($data);

?>