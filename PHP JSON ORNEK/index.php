<?php
$user="demo";
$pass="demo";
if(isset($_POST["user"])){


	$postuser=xss_clear("user");
	$postpass=xss_clear("pass");
	$postparam=xss_clear("param");


	if($user==$postuser && $pass==$postpass){


		require ("Service.php");

		$data=new Service();

		if($postparam=="favorite"){

			echo $data->favorite();


		} elseif($postparam=="category"){


			echo $data->category();

		} elseif($postparam=="categoryItems"){

			$postcatid=xss_clear("catid");

			echo $data->categoryItems($postcatid);

		} else {

			echo json_encode(array("msg"=>"Parametre Hatalı"));
		}





	} else {


		echo json_encode(array("msg"=>"Kullanıcı Bilgileri Hatalı"));


	}
}


function xss_clear($par){
	return htmlspecialchars(addslashes(trim($_POST[$par])));
}
