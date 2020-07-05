<?php
require_once('dbConnect.php');
if($_SERVER['REQUEST_METHOD']=='POST') {

  $search = $_POST['search'];
  $sql = "SELECT * FROM faskes where nama LIKE '%$search%' ORDER BY nama ASC";
  $res = mysqli_query($con,$sql);
  $result = array();
  while($row = mysqli_fetch_array($res)){
    array_push($result, array('id'=>$row[0], 'tipe'=>$row[1], 'jenis'=>$row[2], 'nama'=>$row[3], 'alamat'=>$row[4], 'kecamatan'=>$row[5], 'latitude'=>$row[6], 'longitude'=>$row[7]));
  }
  echo json_encode(array("value"=>1,"allFaskess"=>$result));
  mysqli_close($con);
}

?>