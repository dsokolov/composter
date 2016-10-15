<?php
    function add($id, $userId, $routeNumber, $vehicleNumber, $vehicleColor, $vehicleType, $price, $currency) {
        $query = "INSERT INTO route(id, user_id, route_number, vehicle_number, price, currency, vehicle_color, vehicle_type) VALUES('".$id."', '".$userId."', '".$routeNumber."', '".$vehicleNumber."', ".$price.", '".$currency."', '".$vehicleColor."', '".$vehicleType."');";
		mysql_query($query); 
    }
    
    function getVehicleByUser($userId) {
	    $query = "SELECT * FROM route WHERE user_id = '".$userId."'";
	    $result = mysql_query($query);
		$num_results = mysql_num_rows($result);
		$response = array();
		if ($num_results > 0) {
			$row = mysql_fetch_array($result);
			$response = convertRowVehicleToArray($row);
		}
		return json_encode($response);
    }
    
    function getVehicleById($id) {
        $query = "SELECT * FROM route WHERE id = '".$id."'";
	    $result = mysql_query($query);
		$num_results = mysql_num_rows($result);
		$response = array();
		if ($num_results > 0) {
			$row = mysql_fetch_array($result);
			$response = convertRowVehicleToArray($row);
		}
		return json_encode($response);
    }
    
    function convertRowVehicleToArray($row) {
        $response = array();
        $id = $row["id"];
		$userId = $row["user_id"];
		$routeNumber = $row["route_number"];
		$vehicleNumber = $row["vehicle_number"];
		$vehicleColor = $row["vehicle_color"];
		$vehicleType = $row["vehicle_type"];
		$price = $row["price"];
		$currency = $row["currency"];
		$response = array("id"=>$id, "userId"=>$userId, "routeNumber"=>$routeNumber, "vehicleNumber"=>$vehicleNumber, "vehicleColor"=>$vehicleColor, "vehicleType"=>$vehicleType, "price"=>$price, "currency"=>$currency);
		return $response;
    }
?>