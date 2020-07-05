<?php
	class MapService{
		private $id;
		private $order;

		function __construct($id){
			$this->id = $id;
		}  


		//UNTUK PENGEMBANGAN MULTI GRAPH PER USER, CODINGAN INI DIGANTI
		function Load(){
			$db = new Connection();
			$db->Query("SELECT `file` FROM graph WHERE `user` = :user");
			$db->bindParam(":user", $this->id);
			$db->executeQuery();
			if ($db->hasRow()){
				//LOAD FILE
				$row = $db->Single();
				$filename = $row['file'];
			}else{
				$filename = "default";//LOAD DEFAULT FILE
			}
			
			$handle = fopen("source/".$filename, 'r') or die('Cannot open file:  '."source/".$filename);
			$data = fread($handle,filesize("source/".$filename));
			fclose($handle);
			return $this->myEncode($data);
		}

		function myEncode($json){
        	return "\"".str_replace("\"", "\\\"", rtrim(ltrim($json, "\""), "\""))."\"";
		}

		function Save($data){
			$db = new Connection();
			$db->Query("SELECT `file`, `order` FROM graph WHERE `user` = :user");
			$db->bindParam(":user", $this->id);
			$db->executeQuery();
			if ($db->hasRow()){
				//update file
				$row = $db->Single();
				$this->order = $row['order'];
				$filename = $row['file'];
			}else{
				//Create new file and insert db
				$this->order = 0;
				$filename = $this->uniqueFile();
				
				$db->Query("INSERT INTO `graph` (`user`, `file`, `order`) VALUES (:user, :file, :order)");
				$db->bindParams([
						[":user", $this->id],
						[":file", $filename],
						[":order", $this->order]
					]);
				$db->executeQuery();

			}
				$handle = fopen("source/".$filename, 'w') or die('Cannot open file:  '."source/".$filename);
				fwrite($handle, $data);
				fclose($handle);
		}
		//=========================================================================
		function uniqueFile(){
			return $this->id."_".time()."_".$this->order;
		}
	}