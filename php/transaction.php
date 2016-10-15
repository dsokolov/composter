<?php
    include "utils.php";
    include "user_utils.php";
    include "transaction_utils.php";
    $method = $_REQUEST['m'];
    
    if ($method == "submit") {
        if (tryConnectToDB(HOST, USER, PASS)) {
			mysql_select_db(DB);    
		    $jsonObj = json_decode(file_get_contents('php://input'), true);
		    $jsonObj = (array) $jsonObj;
            $id = uniqid();
		    $routeNumber = $jsonObj['route_number'];
		    $vehicleNumber = $jsonObj['vehicle_number'];
		    $price = $jsonObj["price"];
		    $currency = $jsonObj["currency"];
			$timestamp = $jsonObj["timestamp"];
			$paymentId = $jsonObj["payment_id"];
			$publicanId = $jsonObj["publican_id"];
			$publicanSign = $jsonObj["publican_sign"];
			$payerId = $jsonObj["payer_id"];
			$payerSign = $jsonObj["payer_sign"];
			if (isUserExistById($payerId)) {
			    if (isUserExistById($publicanId)) {
			        if (!yetHasPayment($paymentId)) {
			            submit($id, $routeNumber, $vehicleNumber, $price, $currency, $timestamp, $paymentId, $publicanId, $publicanSign, $payerId, $payerSign);
			            moneyTransfer($price, $payerId, $publicanId);
			            setJsonAnswerHeader();
			            echo json_encode(getTransactionState($id, $payerId, $publicanId));
			        } else {
			            echo getErrorJson("Платеж с идентифкатором ".$paymentId." уже существует"); 
			        }
			    } else {
			        echo getErrorJson("Получатель с идентификатором ".$publicanId." не обнаружен"); 
			    }
			} else {
			    echo getErrorJson("Плательщик с идентификатором ".$playerId." не обнаружен"); 
			}
			
        }
    } else {
	    echo getErrorJson("не известный метод".$method);
    }
    
?>