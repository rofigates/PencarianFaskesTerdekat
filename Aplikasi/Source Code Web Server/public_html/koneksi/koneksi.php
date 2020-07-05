<?php
class koneksi
{
 private $host='localhost';
 private $user='root';
 private $pass='';
 private $dbname='db_faskes';
 private $dbms='mysql';
 public $conn;

public function getConnection()
{
	$this->conn = null;
	try {
	// buat koneksi dengan database
	$this->conn = new PDO($this->dbms.":host=".$this->host.";dbname=".$this->dbname,$this->user,$this->pass);
	
	}
	catch (PDOException $e)
	{
		//tampilkan pesan kesalahan jika koneksi gagal
		echo "Koneksi error..... :" . $e->getMessage();
		die();
	}
	return $this->conn;
}
}
?>