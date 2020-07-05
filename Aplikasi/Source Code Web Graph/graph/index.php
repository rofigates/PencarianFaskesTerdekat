<?php
  ob_start();
  session_start();
  if ((isset($_SESSION['ses_user']) && isset($_SESSION['ses_pass'])) && (!empty($_SESSION['ses_user']) && !empty($_SESSION['ses_pass']))) {
      include "dasboard.php";
  }else{
      include "login.php";
  }
?>
