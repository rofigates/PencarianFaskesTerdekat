<?php

require_once '../koneksi/koneksi.php';

$database = new koneksi();
$db=$database->getConnection();

$query = $db->prepare ("SELECT * FROM faskes WHERE tipe='FaskesTingkat2'");
$query->execute();
$q = $query->fetchAll(PDO::FETCH_ASSOC);
$data = "{fk2:".json_encode($q)."}";
print($data);

?>