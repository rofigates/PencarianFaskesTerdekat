<!DOCTYPE html>
<?php

error_reporting(0);
session_start();
if (!isset($_SESSION['u']) || !isset($_SESSION['p'])){
    header("location:login.php");
}

require_once '../koneksi/koneksi.php';

$database = new koneksi();
$db=$database->getConnection();

?>
<html>

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  
    
<link rel='stylesheet' href='assets/css/fullcalendar.css'>
<link rel='stylesheet' href='assets/css/datatables/datatables.css'>
<link rel='stylesheet' href='assets/css/datatables/bootstrap.datatables.css'>
<link rel='stylesheet' href='assets/scss/chosen.css'>
<link rel='stylesheet' href='assets/scss/font-awesome/font-awesome.css'>
<link rel='stylesheet' href='assets/css/app.css'>

  <link href='http://fonts.googleapis.com/css?family=Oswald:300,400,700|Open+Sans:400,700,300' rel='stylesheet' type='text/css'>

  <link href="assets/favicon.ico" rel="shortcut icon">
  <link href="assets/apple-touch-icon.png" rel="apple-touch-icon">
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
    @javascript html5shiv respond.min
  <![endif]-->

   <title>Fasilitas Kesehatan Kota Malang</title>
<script type="text/javascript">
    var elems = document.getElementsByClassName('confirmation');
    var confirmIt = function (e) {
        if (!confirm('Apakah anda yakin?')) e.preventDefault();
    };
    for (var i = 0, l = elems.length; i < l; i++) {
        elems[i].addEventListener('click', confirmIt, false);
    }
</script>

</head>

<body>


<div class="all-wrapper">
  <div class="row">
    <div class="col-md-3">
      <div class="text-center">
  <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
    <span class="sr-only">Toggle navigation</span>
    <span class="icon-bar"></span>
    <span class="icon-bar"></span>
    <span class="icon-bar"></span>
  </button>
</div>
<div class="side-bar-wrapper collapse navbar-collapse navbar-ex1-collapse">
  <a href="admin.php" class="logo hidden-sm hidden-xs">
    <i class="icon-medkit"></i>
    <span>Admin</span>
  </a>

  <div class="relative-w">
    <ul class="side-menu">
      <li class='current'>
            <a class='current' href="admin.php">
              <i class="icon-th-list"></i> Data Fasilitas kesehatan
            </a>
      </li>      
      <li>
        <a href="tambah.php">
          <i class="icon-edit"></i> Tambah Data
        </a>
      </li>     
      <li>
        <a href="logout.php">
          <span class="badge pull-right"></span>
          <i class="icon-signout"></i> Logout
        </a>
      </li>
    </ul>
  </div>
</div>
    </div>
    <div class="col-md-9">

      <div class="content-wrapper">
        <div class="content-inner">
          <div class="page-header">

  <h1><i class="icon-th-list"></i> Tabel Fasilitas Kesehatan</h1>
</div>
          <div class="main-content">
            <div class="widget">
              <h3 class="section-title first-title"><i class="icon-table"></i> Data Fasilitas Kesehatan</h3>
              <div class="widget-content-white glossed">
                <div class="padded">
                <table class="table table-striped table-bordered table-hover datatable">
                  <thead>
                  <tr>
                    <th>Id</th>
                    <th>Tipe Faskes</th>
                    <th>Jenis Faskes</th>                    
                    <th>Nama Faskes</th>
                    <th>Alamat</th>
                    <th>Kecamatan</th>
                    <th>Telepon</th>
                    <th>Edit</th>
                    <th>Delete</th>
                  </tr>
                  </thead>
                  <tbody>
                    <?php
                  $temp = 0;
                  $query = $db->prepare('SELECT * from faskes');
                  $query->execute();
                  $q = $query->fetchAll(PDO::FETCH_ASSOC);
                  foreach ($q as $key => $value) {
                      echo "<tr>";
                      echo "<td>". $value['id'] ."</td>";
                      echo "<td>". $value['tipe'] ."</td>";
                      echo "<td>". $value['jenis'] ."</td>";                      
                      echo "<td>". $value['nama'] ."</td>";
                      echo "<td>". $value['alamat'] ."</td>";
                      echo "<td>". $value['kecamatan'] ."</td>";
                      echo "<td>". $value['telepon'] ."</td>";
                  ?>
                  <td class="text-center">                        
                        <a href="edit.php?id=<?php echo $value['id'] ?>"<button type="button" class="btn btn-success btn-md"><i class="icon-edit"></i></button></a>
                  </td>
                  <td class="text-center">
                        <a onclick="return confirm('Apakah anda yakin?')" href="delete.php?id=<?php echo $value['id'] ?>" class="btn btn-danger btn-md"><i class="icon-remove"></i></a>
                  </td>
                     
                  <?php                      
                      echo "</tr>";
                      $temp ++;
                      if ($temp == 1000) {
                      break;
                    }
                  }
                ?>               
                    
                  </tbody>
                </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</div>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src='assets/js/jquery.sparkline.min.js'></script>
<script src='assets/js/bootstrap/tab.js'></script>
<script src='assets/js/bootstrap/dropdown.js'></script>
<script src='assets/js/bootstrap/collapse.js'></script>
<script src='assets/js/bootstrap/transition.js'></script>
<script src='assets/js/bootstrap/tooltip.js'></script>
<script src='assets/js/jquery.knob.js'></script>
<script src='assets/js/fullcalendar.min.js'></script>
<script src='assets/js/datatables/datatables.min.js'></script>
<script src='assets/js/chosen.jquery.min.js'></script>
<script src='assets/js/datatables/bootstrap.datatables.js'></script>
<script src='assets/js/raphael-min.js'></script>
<script src='assets/js/morris-0.4.3.min.js'></script>
<script src='assets/js/for_pages/color_settings.js'></script>
<script src='assets/js/application.js'></script>

<script src='assets/js/for_pages/table.js'></script>

</body>

</html>