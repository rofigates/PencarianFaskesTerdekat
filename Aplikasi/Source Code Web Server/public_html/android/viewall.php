<?php

require_once '../koneksi/koneksi.php';

$database = new koneksi();
$db=$database->getConnection();

$query = $db->prepare ("SELECT * FROM `faskes` WHERE jenis!='DPP' AND jenis!='DokterGigi'");
$query->execute();
$q = $query->fetchAll(PDO::FETCH_ASSOC);
$data = "{faskes:".json_encode($q)."}";
print($data);

?>