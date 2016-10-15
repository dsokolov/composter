<?php
    include "utils.php";
    include "user_utils.php";
    include "route_utils.php";
	$method = $_REQUEST['m'];
    if ($method == "add") {
        $jsonObj = json_decode(file_get_contents('php://input'), true);
		$jsonObj = (array) $jsonObj;
        $id = uniqid();
        $userId = $jsonObj["user_id"];
		$routeNumber = $jsonObj["route_number"];
		$vehicleNumber = $jsonObj["vehicle_number"];
		$vehicleColor = $jsonObj["color"];
		$vehicleType = $jsonObj["type"];
		$price = $jsonObj['price'];
		$currency = $jsonObj['currency'];
		if (tryConnectToDB(HOST, USER, PASS)) {
			mysql_select_db(DB);
			if (isUserExistById($userId)) {
			    if (isUserDriver($userId)) {
			        add($id, $userId, $routeNumber, $vehicleNumber, $vehicleColor, $vehicleType, $price, $currency);
                    setJsonAnswerHeader();
                    echo getVehicleById($id);
			    } else {
			        echo getErrorJson("данные по транспортному средству может указывать только водитель");
			    }
			} else {
			    echo getErrorJson("пользователь с логином ".$name." уже существует");
			}
		}    
    } else {
	    echo getErrorJson("не известный метод".$method);
	}
?>