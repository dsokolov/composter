<?php
	define('STRING_EMPTY', '');
	define('HOST', 'localhost');
	define('DB', 'elizar9l_compost');
	define('USER', 'elizar9l_compost');
	define('PASS', 'seven77');
	
	function getErrorJson($msg) {
		header('Content-Type: application/json; charset=utf-8', true);
		$msg = mb_convert_encoding($msg, 'utf-8', mb_detect_encoding($msg));
		$response = array('errorMessage' => $msg);
		return json_encode($response);
	}
	
	function setJsonAnswerHeader() {
	    header('Content-Type: application/json; charset=utf-8', true);
	}
	
	function tryConnectToDB($host, $user, $pass) {
	   	$db = mysql_pconnect($host, $user, $pass);
		if (!$db) {
			echo getErrorJson('Сервер недоступен '.mysql_error());
			return false;
		}
		return true;
	}

?>