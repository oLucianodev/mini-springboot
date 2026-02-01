package br.com.isiflix.webtestapp.service;

import io.isiflix.isispring.annotations.IsiService;

@IsiService
public class IServiceimplemetion implements Iservice {

	@Override
	public String sayCustomMessage(String message) {
		// TODO Auto-generated method stub
		return "Isi" + message;
	}
	

}
