<?php

require_once '../koneksi/koneksi.php';

$database = new koneksi();
$db=$database->getConnection();

$query = $db->prepare ("SELECT DISTINCT(kecamatan) FROM faskes");
$query->execute();
$q = $query->fetchAll(PDO::FETCH_ASSOC);
$data = "{kecamatan:".json_encode($q)."}";
print($data);

?>