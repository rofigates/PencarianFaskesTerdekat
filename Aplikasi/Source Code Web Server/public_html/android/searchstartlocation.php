<?php

require_once '../koneksi/koneksi.php';

$database = new koneksi();
$db=$database->getConnection();

$id = $_POST['id'];

$query = $db->prepare("SELECT nama, latitude, longitude FROM lokasi WHERE id = :id");
$query->bindParam(':id',$id);
$query->execute();
$q = $query->fetchAll(PDO::FETCH_ASSOC);
$data = "{faskes:".json_encode($q)."}";
print($data);

?>