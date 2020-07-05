<!DOCTYPE html>
<html lang="en">
<head>
    <title>Graph Feature</title>

<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="design/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="design/css/style.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="design/css/form.css">
 <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">

<script src="jquery-1.12.4.min.js"></script>
<script src="script.js"></script>
<script src="design/js/bootstrap.min.js"></script>
<script src="design/js/jquery.backstretch.min.js"></script>
<script src="design/js/scripts.js"></script>
    
    
</head>
<body>


<div class="top-content">
    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>GRAPH FEATURE</strong></h1>
                    <div class="description">
                        <p> Create Your Graph In Here By Login With Your Username And Password</p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>Login</h3>
                            <p>Enter Your Username And Password</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="pesan"></div>
                    <div class="form-bottom">
                        <form action="" method="post" class="login-form" id="formlogin" onsubmit="return false">
                            <div class="form-group">
                                <label class="sr-only" for="username">Username</label>
                                <input type="text" name="username" placeholder="Username" class="form-username form-control">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="password">Password</label>
                                <input type="password" name="password" placeholder="Password" class="form-password form-control">
                            </div>
                            <input type="hidden" name="aksi" value="login">
                            <button type="submit" class="btn">Log In</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
        $(document).ready(function(){
            $('#formlogin').submit(function(){
                sender(
                    "ajaxservice.php", 
                    "post", 
                    $(this).serialize(), 
                    "json", 
                    _sH, _eH
                );
            });
        });

        function _sH (fb){
            //console.log(pesan);
            if (fb.s){
                $(".pesan").html(fb.s);
                //console.log(pesan.s);
            }else{
                /*console.log(pesan);*/
                window.location.href = window.location.href;
            }
        }

        function _eH (){
            alert("1");
        }
    </script>

</body>
</html>