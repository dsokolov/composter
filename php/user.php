<?php
    include "utils.php";
    include "user_utils.php";
	$method = $_REQUEST['m'];
	if ($method == 'register') { 
		$jsonObj = json_decode(file_get_contents('php://input'), true);
		$jsonObj = (array) $jsonObj;
        $id = uniqid();
		$name = $jsonObj['name'];
		$password = $jsonObj['password'];
		$publicKey = $jsonObj['public_key'];
		$role = $jsonObj['role'];
		if (tryConnectToDB(HOST, USER, PASS)) {
			mysql_select_db(DB);
			if (isUserExistById($name)) {
			    register($id, $name, $password, $publicKey, $role);
			    setJsonAnswerHeader();
			    echo getUser($id);		
			} else {
			    echo getErrorJson("пользователь с логином ".$name." уже существует");
			}
		}
	} else if ($method == "auth") {
	    $jsonObj = json_decode(file_get_contents('php://input'), true);
		$jsonObj = (array) $jsonObj;
		$name = $jsonObj["name"];
		$password = $jsonObj['password'];
		if (tryConnectToDB(HOST, USER, PASS)) {
			mysql_select_db(DB);
			if (hasAuth($name, $password)) {
			    setJsonAnswerHeader();
			    echo getUserByAuth($name, $password);
			} else {
			    echo getErrorJson("пользователь с логином ".$name." не существует");
			}
		}
	} else {
	    echo getErrorJson("не известный метод".$m);
	}
?>