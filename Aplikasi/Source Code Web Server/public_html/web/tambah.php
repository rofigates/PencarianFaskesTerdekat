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
      <li>
        <a href="admin.php">
          <i class="icon-th-list"></i> Data Fasilitas kesehatan
        </a>
      </li>
      <li class='current'>
            <a class='current' href="tambah.php">
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
                      $query = $db->prepare("INSERT INTO faskes(tipe,jenis,nama,alamat,kecamatan,telepon,latitude,longitude) VALUES(:tipe, :jenis, :nama, :alamat, :kecamatan, :telepon, :latitude, :longitude)");
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
                            <option>FaskesTingkat1</option>
                            <option>FaskesTingkat2</option>
                            <option>FaskesTingkat3</option>                          
                          </select>
                        </div>
                       <div class="form-group">
                        <label>Jenis Fasilitas Kesehatan</label>
                          <select class="form-control" id="jenis" name="jenis">
                            <option disabled selected>Pilih Jenis Fasilitas Kesehatan</option>
                            <option>Puskesmas</option>
                            <option>RumahSakit</option>
                            <option>Apotek</option>
                            <option>Optik</option>
                            <option>KlinikTniPolri</option>
                            <option>Klinik</option>
                            <option>DPP</option>
                            <option>DokterGigi</option>
                            </select>
                        </div>                       
                        <div class="form-group">
                          <label>Nama Fasilitas Kesehatan</label>
                          <input type="text" class="form-control"  id="nama" name="nama" placeholder="Nama Fasilitas Kesehatan">
                        </div>                        
                        <div class="form-group">
                          <label>Alamat</label>
                            <textarea class="form-control" rows="3" id="alamat" name="alamat" placeholder="cari alamat"></textarea>
                        </div>  
                        <div class="form-group">
                        <label>Kecamatan</label>
                          <select class="form-control" id="kecamatan" name="kecamatan">
                            <option disabled selected>Pilih Kecamatan</option>
                            <option>Kedungkandang</option>
                            <option>Blimbing</option>
                            <option>Klojen</option>
                            <option>Lowokwaru</option>
                            <option>Sukun</option>
                          </select>
                        </div>   
                        <div class="form-group">
                          <label>Telepon</label>
                            <input type="text" class="form-control"  id="telepon" name="telepon" placeholder="No Telepon">
                        </div>                           
                        <label>Coordinate</label>                    
                        <div class="row">
                        <div class="col-md-6">                           
                            <div class="form-group">
                              <label class="control-label"  for="lat"></label>
                                <textarea class="form-control" rows="1" placeholder="latitude" type = "text" name="latitude" id="latitude"></textarea>
                            </div>
                          </div>
                          <div class="col-md-6">
                            <div class="form-group">                          
                              <label class="control-label"  for="lng"></label>
                                <textarea class="form-control" rows="1" placeholder="longitude" type = "text" name="longitude" id="longitude"></textarea>
                            </div>
                          </div>                          
                        </div>
                        <br/>
                      <div class="form-inline" align="center">
                        <button type="submit" name="Submit" class="btn btn-success"><i class="icon-edit"></i> Simpan</button>
                        <button class="btn btn-danger" name=:reset type="reset"><i class="icon-refresh"></i> Reset</button>
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
                          <div class="col-md-12" id="maps" style="height:375px">
                          </div>
                        </div> 
                        <div class="form-group">
                          <center><button type="button" class="btn btn-primary" onclick="cari_alamat()"><i class="icon-map-marker"></i> Check Coordinate</button></center>
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

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBu-GzPFrLgGP55Rh7tpc9styb7jHSIlrc&sensor=false" type="text/javascript"></script>
<script type="text/javascript">
function init(){
 var info_window = new google.maps.InfoWindow();
 // menentukan level zoom
 var zoom = 15;

 // menentukan latitude dan longitude
 var pos = new google.maps.LatLng(-7.9465253, 112.6153954);

 // menentukan opsi peta yang akan di buat
 var options = {
  'center': pos,
  'zoom': zoom,
  'mapTypeId': google.maps.MapTypeId.ROADMAP
 };

 // membuat peta
 var map = new google.maps.Map(document.getElementById('maps'), options);
 info_window = new google.maps.InfoWindow({
  'content': 'loading...'
 });

   var marker = new google.maps.Marker({
          position: pos,
          map: map,
          title: 'Hello World!'
      });
    }
function cari_alamat(){
 // mengambil isi dari textarea dengan id alamat
 var alamat = document.getElementById('alamat').value;

 // membuat geocoder
 var geocoder = new google.maps.Geocoder();
 geocoder.geocode(
  {'address': alamat}, 
  function(results, status) { 
   if (status == google.maps.GeocoderStatus.OK) {
    var info_window = new google.maps.InfoWindow();

    // mendapatkan lokasi koordinat
    var geo = results[0].geometry.location;

    // set koordinat
    var pos = new google.maps.LatLng(geo.lat(),geo.lng());

    // menampilkan latitude dan longitude pada id lat dan lng
    var lat = document.getElementById('latitude').innerHTML = geo.lat();
    var lng = document.getElementById('longitude').innerHTML = geo.lng();

    // opsi peta yang akan di tampilkan
    var option = {
     center: pos,
     zoom: 16,
     mapTypeId:google.maps.MapTypeId.ROADMAP
    };

    // membuat peta
    var map = new google.maps.Map(document.getElementById('maps'),option);
    info_window = new google.maps.InfoWindow({
     content: 'loading...'
    });

    // menambahkan marker pada peta
    var marker = new google.maps.Marker({
     position: pos,
     title: 'You are here',
     animation:google.maps.Animation.BOUNCE
    });
    marker.setMap(map);

    // menambahkan event click ketika marker di klik
    google.maps.event.addListener(marker, 'click', function () {
     info_window.setContent('<b>'+ this.title +'</b>');
     info_window.open(map, this);
    });
   } else {
    alert('Lokasi Tidak Ditemukan'); 
   } 
  }
 );
}
google.maps.event.addDomListener(window, 'load', init);
</script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Menu Toggle Script -->
    <script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
    </script>


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