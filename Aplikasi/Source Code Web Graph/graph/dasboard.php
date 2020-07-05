<!DOCTYPE html>
<html>
  <head>
    <style>
       #map {
        width: 100%;
        height: 500px;
      }
    </style>
    <style type="text/css">
        body {
          margin: 0;
          padding: 0;
        }
        .btn-ok {
          position: absolute;
          bottom: 0;
          width: 30px;
          height: 15px;
          padding: 3px;
          background: orange;
          color: #fff;
          font-size: 18px;
          text-align: center;
          margin-bottom: -20px;
          margin-left: -1px;
        }
        .btn-ok:hover {
          cursor: pointer;
        }
        .wrap {
          position: absolute;
          top: 0;
          background: #eaeaea;
          width: 300px;
          height: 300px;
          margin-top: -302px;
          border: 1px solid #666;
        }
        .loading-ajax {
            width: 100%;
            height: 100%;
            background: #a5a5a5;
            z-index: 100;
            bottom: 0;
        }
        .button {
        background-color: #0600ff; 
        border: none;
        color: white;
        padding: 12px 28px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 12px;
        margin: 4px 2px;
        cursor: pointer;
        -webkit-transition-duration: 0.4s; /* Safari */
        transition-duration: 0.4s;
        }
        .button2:hover {
            box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
        }
        </style>

  </head>
  <body>
  
    <div name="content">
      <div id="map"></div>
      <div class="wrap">
        <div class="btn-ok open">+</div>
        <div id="demo" align="center"><h2>Bakalidev.com</h2></div>
        <div align="center"><p> Graph Feature is graph marker that can help you to make graph
        just by login username and password and <strong>free.</strong>
        </p></div>
        <div align="center">It's still beta version will update soon</div><br><br>
        <div align="center">
        <button type="button" class="button button2" id="ee">Save Graph</button>
        </div>
      </div>
    </div>
    <script src="jquery-1.12.4.min.js"></script>
    <script src="script.js"></script>
    <script type="text/javascript" src="variable.js"></script>
    <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBTaA6qwepDwxob-FmWgUwvYo2LNcmUFOA&&callback=initMap"></script>
    <script type="text/javascript" src="edited js.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $('#ee').click(function(){
                sender(
                    "ajaxservice.php", 
                    "post", 
                    "data="+JSON.stringify(map_cls)+"&aksi=maps", 
                    "html", 
                    _sH, _eH
                );
            });
        });
        
        function _sH(fb){
          alert(fb);
        }

        function _eH(){
          alert("gagal");
        }

        $('#map').css('height', $(window).height());
        $('.btn-ok').click(function () {
          $(this).toggleClass('open');
          if ($(this).hasClass('open')) {
            $('.wrap').animate({
              'margin-top': '-302px'
            });
          } else {
            $('.wrap').animate({
              'margin-top': '0'
            });
          }
        });
    </script>
  </body>
</html>