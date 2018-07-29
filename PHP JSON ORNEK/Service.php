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
			array("id"=>"4","title"=>"Rahatlama ve meditayson","url"=>"http://mobiluygulama.pro/webservice/img/4.jpg")
		);

		return json_encode(array("success"=>$datas));


	}

	public function categoryItems($categoryId){


		$datas=array(
		array("id"=>"1","title"=>"Karışık Kuş Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/kus1.mp3","image"=>"http://mobiluygulama.pro/webservice/img/kus1.jpg","catid"=>"1"),
			array("id"=>"2","title"=>"Bülbül Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/bulbul.mp3","image"=>"http://mobiluygulama.pro/webservice/img/kus2.jpg","catid"=>"1"),
			array("id"=>"3","title"=>"Karga Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/karga.mp3","image"=>"http://mobiluygulama.pro/webservice/img/kus3.jpg","catid"=>"1"),
			
			
			array("id"=>"4","title"=>"Bizim Hikaye","url"=>"http://mobiluygulama.pro/webservice/mp3/piyano/bizimhikaye.mp3","image"=>"http://mobiluygulama.pro/webservice/img/piyano1.jpg","catid"=>"2"),
			array("id"=>"5","title"=>"Titanik Piyano","url"=>"http://mobiluygulama.pro/webservice/mp3/piyano/titanik.mp3","image"=>"http://mobiluygulama.pro/webservice/img/piyano2.jpg","catid"=>"2"),
			array("id"=>"6","title"=>"Rahatlatıcı Piyano","url"=>"http://mobiluygulama.pro/webservice/mp3/piyano/piyano1.mp3","image"=>"http://mobiluygulama.pro/webservice/img/piyanoo3.jpg","catid"=>"2"),
			
			array("id"=>"7","title"=>"Su Havuzu","url"=>"http://mobiluygulama.pro/webservice/mp3/doga/suhavuzu.mp3","image"=>"http://mobiluygulama.pro/webservice/img/doga1.jpg","catid"=>"3"),
			array("id"=>"8","title"=>"Orman Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/doga/ormansesi.mp3","image"=>"http://mobiluygulama.pro/webservice/img/doga2.jpg","catid"=>"3"),
			array("id"=>"9","title"=>"Rüzgar Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/doga/ruzgarsesi.mp3","image"=>"http://mobiluygulama.pro/webservice/img/doga3.jpg","catid"=>"3"),
			array("id"=>"10","title"=>"Doğa Sesi","url"=>"http://mobiluygulama.pro/webservice/mp3/doga/doga.mp3","image"=>"http://mobiluygulama.pro/webservice/img/doga4.jpg","catid"=>"3"),
			array("id"=>"11","title"=>"Su Damlası","url"=>"http://mobiluygulama.pro/webservice/mp3/doga/damlalisu.mp3","image"=>"http://mobiluygulama.pro/webservice/img/doga5.jpg","catid"=>"3"),
			
			
			array("id"=>"12","title"=>"Duygusal Fon Müziği","url"=>"http://mobiluygulama.pro/webservice/mp3/meditayson/duygusalfon.mp3","image"=>"http://mobiluygulama.pro/webservice/img/meditasyon1.jpg","catid"=>"4"),
			array("id"=>"13","title"=>"Relax ","url"=>"http://mobiluygulama.pro/webservice/mp3/meditayson/relax.mp3","image"=>"http://mobiluygulama.pro/webservice/img/meditasyon2.jpg","catid"=>"4"),
			array("id"=>"14","title"=>"Ruhu Dinlendiren 1","url"=>"http://mobiluygulama.pro/webservice/mp3/meditayson/ruhudinlendiren.mp3","image"=>"http://mobiluygulama.pro/webservice/img/meditasyon3.jpg","catid"=>"4"),
			array("id"=>"15","title"=>"Ruhu Dinlendiren 2","url"=>"http://mobiluygulama.pro/webservice/mp3/meditayson/ruhudinlendiren2.mp3","image"=>"http://mobiluygulama.pro/webservice/img/meditasyon4.jpg","catid"=>"4")
			
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