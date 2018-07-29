<?php

class Service{


	public function favorite(){

		$datas=array(
			
			array("id"=>"1","title"=>"Karışık Kuş Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/kus1.mp3","image"=>"https://www.bestepebloggers.com/wp-content/uploads/2018/05/ku%C5%9F-isimleri.jpg","catid"=>"1"),
			array("id"=>"2","title"=>"Bülbül Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/bulbul.mp3","image"=>"http://kuslar.biz/resimler/73431707965.jpg","catid"=>"1"),
			array("id"=>"3","title"=>"Karga Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/karga.mp3","image"=>"https://emoji.com.tr/wp-content/uploads/2017/10/karga-10.jpg","catid"=>"1")
		);

		return json_encode(array("success"=>$datas));
	}

	public function category(){

		$datas=array(
			array("id"=>"1","title"=>"Kuş Sesleri","url"=>"http://mobiluygulama.pro/webservice/img/1.jpg"),
			array("id"=>"2","title"=>"Piyano Sesleri","url"=>"http://mobiluygulama.pro/webservice/img/2.jpg"),
			array("id"=>"3","title"=>"Doğa Sesleri","url"=>"http://mobiluygulama.pro/webservice/img/3.jpg"),
			array("id"=>"4","title"=>"Rahatlama ve Meditasyon","url"=>"http://mobiluygulama.pro/webservice/img/4.jpg")
		);

		return json_encode(array("success"=>$datas));


	}

	public function categoryItems($categoryId){


		$datas=array(
		array("id"=>"1","title"=>"Karışık Kuş Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/kus1.mp3","image"=>"https://www.bestepebloggers.com/wp-content/uploads/2018/05/ku%C5%9F-isimleri.jpg","catid"=>"1"),
			array("id"=>"2","title"=>"Bülbül Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/bulbul.mp3","image"=>"http://kuslar.biz/resimler/73431707965.jpg","catid"=>"1"),
			array("id"=>"3","title"=>"Karga Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/karga.mp3","image"=>"https://emoji.com.tr/wp-content/uploads/2017/10/karga-10.jpg","catid"=>"1")
		);

		$newdata="";
		foreach($datas as $data){

			if($data["catid"]==$categoryId){
				$newdata[]=$data;
			}

		}

		return json_encode(array("success"=>$newdata));

	}



}