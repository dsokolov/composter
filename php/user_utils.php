<?php
    include "route_utils.php";
    
	function getUser($id) {
	    $query = "SELECT * FROM user WHERE id = '".$id."'";
	    $result = mysql_query($query);
		$num_results = mysql_num_rows($result);
		$response = array();
		if ($num_results > 0) {
			$row = mysql_fetch_array($result);
			$response = convertUserRowArray($row);
		}
		return json_encode($response);
	}
	
	function getUserByAuth($name, $password) {
	    $query = "SELECT * FROM user WHERE name = '".$name."' AND hash = '".$password."'";
	    $result = mysql_query($query);
		$num_results = mysql_num_rows($result);
		$response = array();
		if ($num_results > 0) {
			$row = mysql_fetch_array($result);
			$response = convertUserRowArray($row);
		}
		return json_encode($response);
	}
	
	function convertUserRowArray($row) {
	    $result = array();
	    $id = $row["id"];
		$name = $row["name"];
		$balance = $row["balance"];
		$role = $row["role"];
		$result = array("id"=>$id, "name"=>$name, "balance"=>$balance, "role"=>$role);
		if ($role == 1) {
		    $query = "SELECT * FROM route WHERE user_id = '".$id."'";
	        $r = mysql_query($query);
		    $num_results = mysql_num_rows($r);
		    $response = array();
		    if ($num_results > 0) {
			    $rowVehicle = mysql_fetch_array($r);
			    $response = convertRowVehicleToArray($rowVehicle);
		    }
		    $result = array("id"=>$id, "name"=>$name, "balance"=>$balance, "role"=>$role, "vehicle"=>$response);
		}
		return $result;
	}
	
	function hasAuth($name, $password) {
	    $query = "SELECT * FROM user WHERE name = '".$name."' AND hash = '".$password."'";
	    $result = mysql_query($query);
		$num_results = mysql_num_rows($result);
		return $num_results > 0;
	}
	
	function isUserExist($name) {
	    $query = "SELECT * FROM user WHERE name = '".$name."'";
	    $result = mysql_query($query);
		$num_results = mysql_num_rows($result);
		return $num_results > 0;
	}
	
	function isUserExistById($id) {
	    $query = "SELECT * FROM user WHERE id = '".$id."'";
	    $result = mysql_query($query);
		$num_results = mysql_num_rows($result);
		return $num_results > 0;
	}
	
	function register($id, $name, $password, $publicKey, $role) {
	    $query = "INSERT INTO user(id, name, public_key, balance, role, hash) VALUES('".$id."', '".$name."', '".$publicKey."', 0, ".$role.", '".$password."');";
		mysql_query($query);
	}
	
	function isUserDriver($id) {
	    $result = false;
	    $query = "SELECT * FROM user WHERE id = '".$id."'";
	    $result = mysql_query($query);
		$num_results = mysql_num_rows($result);
		if ($num_results > 0) {
			$row = mysql_fetch_array($result);
			$role = $row["role"];
			$result = $role == 1;
		}
		return $result;
	}
	
	function getBalance($id) {
	    $result = 0;
	    $query = "SELECT * FROM user WHERE id = '".$id."'";
	    $result = mysql_query($query);
		$num_results = mysql_num_rows($result);
		if ($num_results > 0) {
			$row = mysql_fetch_array($result);
			$balance = $row["balance"];
			$result = $balance;
		}
		return $result;
	}
	
	function getCurrency($id) {
	    $result = 0;
	    $query = "SELECT * FROM user WHERE id = '".$id."'";
	    $result = mysql_query($query);
		$num_results = mysql_num_rows($result);
		if ($num_results > 0) {
			$row = mysql_fetch_array($result);
			$result = $row["currence"];
		}
		return $result;
	}
	
	function setBalance($id, $newBalance) {
	    $query = "UPDATE user SET `balance`=".$newBalance." WHERE id = '".$id."';";
		return mysql_query($query);
	}
?>