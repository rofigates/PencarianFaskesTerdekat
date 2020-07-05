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

$id = $_GET['id'];
if(isset($id)){
  $stt = $db->prepare('SELECT tipe, jenis, nama, alamat, kecamatan, telepon, latitude, longitude from faskes where id = :id');
  $stt->bindParam(':id', $id);
  if($stt->execute()){
    $q = $stt->fetchAll(PDO::FETCH_ASSOC);
    foreach ($q as $key => $value) {
      $tipe_awal = $value['tipe'];
      $jenis_awal = $value['jenis'];      
      $nama_awal = $value['nama'];
      $alamat_awal = $value['alamat'];
      $kecamatan_awal = $value['kecamatan'];      
      $telepon_awal = $value['telepon'];
      $lat_awal = $value['latitude'];
      $lng_awal = $value['longitude'];
    }
  }
  
}

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
  <h1><i class="icon-edit"></i> Tambah Data Fasilitas Kesehatan</h1>

<?php
                $button = $_POST['Submit'];
                if(isset($button)){

                    $tipe = $_POST['tipe'];
                    $jenis = $_POST['jenis'];                    
                    $nama = $_POST['nama'];
                    $alamat = $_POST['alamat'];
                    $kecamatan = $_POST['kecamatan'];
                    $telepon = $_POST['telepon'];
                    $lat = $_POST['latitude'];
                    $lng = $_POST['longitude'];

                    if(empty($tipe) || empty($jenis) ||empty($nama) || empty($alamat) || empty($kecamatan) || empty($telepon) || empty($lat) || empty($lng)){
                      echo "<div class='alert alert-danger'>";
                      echo "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>";
                      echo "<strong>Error !</strong> Lengkapi Data Terlebih Dahulu";
                      echo "</div>";
                    }else{
                     $query = $db->prepare("UPDATE faskes SET tipe = :tipe, jenis = :jenis, nama = :nama, alamat = :alamat, kecamatan = :kecamatan, telepon = :telepon, latitude = :latitude,  longitude = :longitude WHERE id = :id");
                      $query->bindParam(':id', $id);
                      $query->bindParam(':tipe', $tipe);
                      $query->bindParam(':jenis', $jenis);
                      $query->bindParam(':nama', $nama);
                      $query->bindParam(':alamat', $alamat);
                      $query->bindParam(':kecamatan', $kecamatan);
                      $query->bindParam(':telepon', $telepon);
                      $query->bindParam(':latitude', $lat);
                      $query->bindParam(':longitude', $lng);
                       if($query->execute()){
                        echo "<div class='alert alert-success'>";
                        echo "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>";
                        echo "<strong>Sukses !</strong> Data Telah Tersimpan";
                        echo "</div>";
                        header('location:admin.php');
                      }else{
                        echo "<div class='alert alert-danger'>";
                        echo "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>";
                        echo "<strong>Gagal !</strong> Gagal Menyimpan Data";
                        echo "</div>";
                      }
                    }    
                }  
              ?>
</div>

          <div class="main-content">
            <div class="row">
              <div class="col-md-6">
                <div class="widget">
                  <div class="widget-content-white glossed">
                    <div class="padded">
                      <form action="" role="form" method="post">                    
                        <h3 class="form-title form-title-first"><i class="icon-th-list"></i> Form Fasilitas Kesehatan</h3>
                        <div class="form-group">
                        <label>Tipe Fasilitas Kesehatan</label>
                          <select class="form-control" id="tipe" name="tipe">
                            <option disabled selected>Pilih Tipe Fasilitas Kesehatan</option>
                            <option value="FaskesTingkat1"<?=$tipe_awal == 'FaskesTingkat1' ? ' selected="selected"' : '';?>>FaskesTingkat1</option>
                            <option value="FaskesTingkat2"<?=$tipe_awal == 'FaskesTingkat2' ? ' selected="selected"' : '';?>>FaskesTingkat2</option>
                            <option value="FaskesTingkat3"<?=$tipe_awal == 'FaskesTingkat3' ? ' selected="selected"' : '';?>>FaskesTingkat3</option>
                          </select>
                        </div>                            
                       <div class="form-group">
                        <label>Jenis Fasilitas Kesehatan</label>
                          <select class="form-control" id="jenis" name="jenis">
                            <option disabled selected>Pilih Jenis Fasilitas Kesehatan</option>
                            <option value="Puskesmas"<?=$jenis_awal == 'Puskesmas' ? ' selected="selected"' : '';?>>Puskesmas</option>
                            <option value="RumahSakit"<?=$jenis_awal == 'RumahSakit' ? ' selected="selected"' : '';?>>RumahSakit</option>
                            <option value="Apotik"<?=$jenis_awal == 'Apotik' ? ' selected="selected"' : '';?>>Apotik</option>
                            <option value="Optik"<?=$jenis_awal == 'Optik' ? ' selected="selected"' : '';?>>Optik</option>
                            <option value="KlinikTniPolri"<?=$jenis_awal == 'KlinikTniPolri' ? ' selected="selected"' : '';?>>KlinikTniPolri</option>
                            <option value="Klinik"<?=$jenis_awal == 'Klinik' ? ' selected="selected"' : '';?>>Klinik</option>
                            <option value="DPP"<?=$jenis_awal == 'DPP' ? ' selected="selected"' : '';?>>DPP</option>
                            <option value="DokterGigi"<?=$jenis_awal == 'DokterGigi' ? ' selected="selected"' : '';?>>DokterGigi</option>                                                        
                          </select>
                        </div>
                        <div class="form-group">
                          <label>Nama Fasilitas Kesehatan</label>
                          <input type="text" class="form-control"  id="nama" name="nama" placeholder="Nama Fasilitas Kesehatan" value="<?php echo $nama_awal ?>">
                        </div>                        
                        <div class="form-group">
                          <label>Alamat</label>                          
                          <textarea class="form-control" rows="3" id="alamat" name="alamat" placeholder="Alamat"><?php echo $alamat_awal ?></textarea>
                        </div>  
                        <div class="form-group">
                        <label>Kecamatan</label>
                          <select class="form-control" id="kecamatan" name="kecamatan">
                            <option disabled selected>Pilih Kecamatan</option>
                            <option value="Kedungkandang"<?=$kecamatan_awal == 'Kedungkandang' ? ' selected="selected"' : '';?>>Kedungkandang</option>
                            <option value="Blimbing"<?=$kecamatan_awal == 'Blimbing' ? ' selected="selected"' : '';?>>Blimbing</option>
                            <option value="Klojen"<?=$kecamatan_awal == 'Klojen' ? ' selected="selected"' : '';?>>Klojen</option>
                            <option value="Lowokwaru"<?=$kecamatan_awal == 'Lowokwaru' ? ' selected="selected"' : '';?>>Lowokwaru</option>
                            <option value="Sukun"<?=$kecamatan_awal == 'Sukun' ? ' selected="selected"' : '';?>>Sukun</option>
                          </select>
                        </div>   
                        <div class="form-group">
                          <label>Telepon</label>
                            <input type="text" class="form-control"  id="telepon" name="telepon" placeholder="No Telepon" value="<?php echo $telepon_awal ?>">
                        </div>   
                        <label>Coordinate</label>                    
                        <div class="row">
                        <div class="col-md-6">                           
                            <div class="form-group">
                              <input type="text" class="form-control" id="latitude" name="latitude" placeholder="Latitude" value="<?php echo $lat_awal ?>">
                            </div>
                          </div>
                          <div class="col-md-6">
                            <div class="form-group">                          
                              <input type="text" class="form-control" id="longitude" name="longitude" placeholder="Longitude" value="<?php echo $lng_awal ?>">
                            </div>
                          </div>                          
                        </div>
                        <br/>
                      <div class="form-inline" align="center">
                        <button type="submit" name="Submit" class="btn btn-success"><i class="icon-edit"></i> Simpan</button>                        
                      </div>
                      </form>
                    </div>
                    <div class="col-md-6">
                    </div>
                  </div>
                </div>
              </div>
               <div class="col-md-6">
                <div class="widget">
                  <div class="widget-content-white glossed">
                    <div class="padded">
                      <form action="" role="form" class="form-maps">                        
                        <h3 class="form-title form-title-first"><i class="icon-map-marker"></i> Maps</h3>               
                        <div class="form-group">
                          <div class="col-md-12" id="map" style="height:375px">
                          </div>
                        </div> 
                        <div class="form-group">
                          <center><button type="button" class="btn btn-primary" onclick="input();"><i class="icon-map-marker"></i> Check Coordinate</button></center>
                        </div>                      
                      </form>
                    </div>
                  </div>
                </div>
              </div>
              
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</div>

 <script>
      var map;
      var latlng;
      var latitude;
      var longitude;

      function awal(){
        latitude = <?php echo $lat_awal?>;
        longitude = <?php echo $lng_awal?>;
        latlng = {lat: parseFloat(latitude) ,lng:  parseFloat(longitude)};
        initMap();
        console.Log("iki ");
      }


      function input(){
        latitude = document.getElementById('latitude').value;
        longitude = document.getElementById('longitude').value;
        latlng = {lat: parseFloat(latitude) ,lng:  parseFloat(longitude)};
        initMap(); 
      }

      function initMap() {
        startlatlng = {lat: -7.9819 , lng: 112.6265};

        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 8,
          center: startlatlng
        });

        var marker = new google.maps.Marker({
          position: latlng,
          map: map
        });
        
      }
      awal();
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyALuq5_TiJR2Eo-k24SO7IL42j5q5e5_3E&callback=initMap"
    async defer></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>


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

<script src='assets/js/for_pages/forms.js'></script>

</body>

</html>