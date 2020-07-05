<?php

require_once '../koneksi/koneksi.php';

$database = new koneksi();
$db=$database->getConnection();

$query = $db->prepare("SELECT id, nama FROM lokasi");
$query->execute();
$q = $query->fetchAll(PDO::FETCH_ASSOC);
$data = "{faskes:".json_encode($q)."}";
print($data);

?>