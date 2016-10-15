<?php
    include('Crypt/RSA.php');
    $rsa = new Crypt_RSA();

    function ($id, $routeNumber, $vehicleNumber, $price, $currency, $timestamp, $paymentId, $publicanId, $publicanSign, $payerId, $payerSign) {
        $query = "INSERT INTO transaction(id, route_number, vehicle_number, price, currency, transaction.timestamp, payment_id, publican_id, publican_sign, payer_id, payer_sign) VALUES('".$id."', '".$routeNumber."', '".$vehicleNumber."', ".$price.", '".$currency."', ".$timestamp.", '".$paymentId."', '".$publicanId."', '".$publicanSign."', '".$payerId."', '".$payerSign."');";
		mysql_query($query); 
    }
    
    function yetHasPayment($id) {
	    $query = "SELECT * FROM transaction WHERE payment_id = '".$id."'";
	    $result = mysql_query($query);
		$num_results = mysql_num_rows($result);
		return $num_results > 0;
	}
	
	function moneyTransfer($summ, $fromUserId, $toUserId) {
	    $currentBalance = getBalance($fromUserId);
	    setBalance($fromUserId, $currentBalance - $summ);
	    $currentBalance = getBalance($toUserId);
	    setBalance($toUserId, $currentBalance + $summ);
	}
	
	function getTransactionState($id, $payerId, $publicanId) {
	    $payer = array("id"=>$payerId, "balance"=>getBalance($payerId));
	    $publican = array("id"=>$publicanId, "balance"=>getBalance($publicanId));
	    return array("payer"=>$payer, "publican"=>$publican, "transactionId"=>$id, "state"=>"success");
	}
	
	function isSignValid($id, $routeNumber, $vehicleNumber, $price, $currency, $timestamp, $paymentId, $publicanId, $publicanSign, $payerId, $payerSign) {
	    
	}
	
?>