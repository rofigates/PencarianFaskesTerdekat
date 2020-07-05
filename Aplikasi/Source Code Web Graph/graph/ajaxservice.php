<?php
error_reporting(E_ALL & ~E_NOTICE);
//include(global_function);
require_once 'class_login.php';
if($_POST['aksi'] == "login"){
    $u = $_POST['username'];
    $p = $_POST['password'];
    $err = [];

    $Login = new Login($u, $p);

    // cek koksong
    if (empty($u) || empty($p)) {
        $err["s"] = "user atau password hasrus diisi";
    }
    // cek karakter
    else if (!$Login->cekKarakter()) {
        $err["s"] = "username menggunakan karakter yang tidak valid";
    }
    // cek user di database
    else if (!$Login->cekUsername()) {
        $err["s"] = "user atau password salah";
    }
    else {
        if (!$Login->cekUserPass()){
            $err["s"] = "user atau password salah";
        }else{
            $err["b"] = "Sukses";
        }
    }
    echo json_encode($err);
}else if($_POST['aksi'] == "maps"){
    $Login = new Login('','');
    if ($Login->isAxistCurrentUser()){
        if (isset($_POST['data']) && !empty($_POST['data'])){
            require_once "mapservice.php";
            $ms = new MapService($Login->id);
            $ms->Save($_POST['data']);
            echo "Success";
        }else{
            echo "Failed";
        }
    }else{
        echo "Failed";
    }
}else if($_POST['aksi'] == "loadmaps"){
    $Login = new Login('','');
    if ($Login->isAxistCurrentUser()){
        require_once "mapservice.php";
        $ms = new MapService($Login->id);
        echo $ms->Load();
    }else{
        echo "Failed";
    }
}else{
    echo "HACKED";
}