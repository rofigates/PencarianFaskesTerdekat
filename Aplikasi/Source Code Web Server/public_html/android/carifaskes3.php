<?php

require_once '../koneksi/koneksi.php';

$database = new koneksi();
$db=$database->getConnection();

$query = $db->prepare ("SELECT * FROM faskes WHERE tipe='FaskesTingkat3'");
$query->execute();
$q = $query->fetchAll(PDO::FETCH_ASSOC);
$data = "{fk3:".json_encode($q)."}";
print($data);

?>