<!DOCTYPE html>
<?php
error_reporting(0);
require_once '../koneksi/koneksi.php';

$database = new koneksi();
$db=$database->getConnection();

?>
<html>

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

<link rel='stylesheet' href='assets/scss/chosen.css'>
<link rel='stylesheet' href='assets/scss/font-awesome/font-awesome.css'>
<link rel='stylesheet' href='assets/css/app.css'>

  <link href='http://fonts.googleapis.com/css?family=Oswald:300,400,700|Open+Sans:400,700,300' rel='stylesheet' type='text/css'>


  <title>Fasilitas Kesehatan Kota Malang</title>

</head>

<body>



<div class="all-wrapper no-menu-wrapper">
  <div class="login-logo-w">
    <a href="index.html" class="logo">
      <i class="icon-ambulance"></i>
      <span>Fasilitas Kesehatan Kota Malang</span>
    </a>
  </div>
  <div class="row">
    <div class="col-md-4 col-md-offset-4">

      <div class="content-wrapper bold-shadow">
        <div class="content-inner">
          <div class="main-content main-content-grey-gradient no-page-header">
            <div class="main-content-inner">
            <form action="admin.php" role="form" method="post">
              <h3 class="form-title form-title-first"><i class="icon-lock"></i> Login Admin</h3>
              <div class="form-group">
                <label>Username</label>
                <input type="text" class="form-control" name="form-username" id="form-username" placeholder="Usename">
              </div>
              <div class="form-group">
                <label>Password</label>
                <input type="password" class="form-control" name="form-password" id="form-password" placeholder="Password">
              </div>             
              <center><button type="submit" name="login" class="btn btn-primary btn-lg"><i class="icon-signin"> Sign In</i></button></center>              
            </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
<?php

$button = $_POST['login'];
if(isset($button)){
    $username = $_POST['form-username'];
    $password = $_POST['form-password'];
    $query = $db->prepare('SELECT `id` from admin where `username` = :username and `password` = :password');
    $query->bindParam(':username', $username);
    $query->bindParam(':password', $password);
    $query->execute();

    if($query->rowCount() > 0){

    session_start();
    $_SESSION['u'] = $username;
    $_SESSION['p'] = $password;
    session_regenerate_id(true);
        header('location:admin.php');
    }else{
        echo "<script type='text/javascript'> alert('Login Failed') </script>";
    }
}
?>

</body>

</html>