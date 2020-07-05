<?php

require_once '../koneksi/koneksi.php';

$database = new koneksi();
$db=$database->getConnection();

$query = $db->prepare ("SELECT DISTINCT(jenis) FROM faskes");
$query->execute();
$q = $query->fetchAll(PDO::FETCH_ASSOC);
$data = "{jenis:".json_encode($q)."}";
print($data);

?>