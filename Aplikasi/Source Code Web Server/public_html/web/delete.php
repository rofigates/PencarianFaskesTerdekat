<?php
error_reporting(0);
session_start();
if (!isset($_SESSION['u']) || !isset($_SESSION['p'])){
    header("location:login.php");
}

if (isset($_GET['id'])){
require_once '../koneksi/koneksi.php';

$database = new koneksi();
$db=$database->getConnection();

$id = $_GET['id'];
$query = $db->prepare("DELETE FROM faskes WHERE id = :id");
$query->bindParam(':id', $id);
$query->execute();
}
header('location:admin.php');
?>