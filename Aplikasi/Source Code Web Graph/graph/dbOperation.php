<?php
	include "config.php";
	class Connection{
		public $servername;
		public $username;
		public $password;
		public $dbname;

		private $conn;
		private $statement;
		private $constr;

		private $error;
		private $iserror;

		function __construct(){
			$this->servername = DB_HOST;
			$this->username = DB_USER;
			$this->password = DB_PASS;
			$this->dbname = DB_NAME;
			$this->constr = "mysql:host=" . $this->servername . ";dbname=" . $this->dbname;

			$options = array(
            	PDO::ATTR_PERSISTENT    => true,
            	PDO::ATTR_ERRMODE       => PDO::ERRMODE_EXCEPTION
        	);

        	try{
        		$this->conn =  new PDO($this->constr, $this->username, $this->password, $options);
        	}catch(PDOException $e){
        		$this->iserror = true;
        		$this->error = $e->getMessage();
        	}
		}

		function Query($query){
			$this->statement = $this->conn->prepare($query);
		}

		function bindParam($param, $value){
			$this->statement->bindParam($param, $value);
		}

		function bindParams($params){
			if (is_array($params)){
				for ($i = 0 ; $i < count($params) ; $i++){
					if (is_array($params[$i]) && 
						(!is_array($params[$i][0]) && !is_array($params[$i][1]))
						){
						$this->bindParam($params[$i][0], $params[$i][1]);
					}
				}
			}
		}

		function executeQuery(){
			return $this->statement->execute();
		}

		function getRecord(){
			return $this->statement->fetchAll(PDO::FETCH_ASSOC);
		}

		function executeNonQuery(){
			$this->executeQuery();
			return $this->getRecord();
			//return $this->statement->fetchAll(PDO::FETCH_ASSOC);
		}

		function Single(){
			$this->executeQuery();
			return $this->statement->fetch(PDO::FETCH_ASSOC);
		}

		function hasRow(){
			if ($this->statement->rowCount() > 0){
				return true;
			}
			return false;
		}

		function rowCount(){
			$this->statement->rowCount();
		}

		function lastInsertId(){
   			return $this->conn->lastInsertId();
		}
		
		function beginTransaction(){
		    return $this->conn->beginTransaction();
		}

		function endTransaction(){
		    return $this->conn->commit();
		}

		function cancelTransaction(){
		    return $this->conn->rollBack();
		}

		function debugDumpParams(){
		    return $this->statement->debugDumpParams();
		}

		function getError(){
			return $this->error;
		}
	}
?>	