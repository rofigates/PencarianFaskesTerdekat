<?php

require_once 'dbOperation.php';

class Login
{
	public $user;
	public $pass;
	public $id;

	function __construct($user, $pass) {
		$this->user = $user;
		$this->pass = $pass;
	}

	function cekKarakter() {
		if (preg_match('#^[A-Za-z0-9]+$#', $this->user)) {
			return true;
		}
		return false;
	}

	function cekUsername() {
		try {
			$db = new Connection();
			$db->Query("SELECT id FROM admin WHERE username = :u");
			$db->bindParam(':u', $this->user);
			$db->executeQuery();
			if ( $db->hasRow() )
				return true;
			return false;
		}
		catch (PDOException $e) {
			// 
		}
	}

	function cekUserPass($check = false){
		try {
			$db = new Connection();
			$db->Query("SELECT * FROM admin WHERE username = :u and password = :p");
			$db->bindParam(':u', $this->user);
			$db->bindParam(':p', $this->pass);
			$db->executeQuery();
			if ( $db->hasRow() ){
					$row = $db->Single();
				if(!$check){
					session_start();
		            $_SESSION['ses_user'] = $row['username'];
		            $_SESSION['ses_pass'] = $row['password'];
		            session_regenerate_id(true);
		        }else{
		        	$this->id = $row['id'];
		        }
				return true;
			}
			return false;
		}
		catch (PDOException $e) {
			// 
		}
	}

	function isAxistCurrentUser(){
		session_start();
		if (
			(
				isset($_SESSION['ses_user']) && 
				isset($_SESSION['ses_pass'])
			) && 
			(
				!empty($_SESSION['ses_user']) && 
				!empty($_SESSION['ses_pass'])
			)
		){
			$this->user = $_SESSION['ses_user'];
			$this->pass = $_SESSION['ses_pass'];
			if ($this->cekUserPass(true)){
				return true;
			}
		}
		return false;
	}
}