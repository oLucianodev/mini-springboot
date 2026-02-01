package br.com.isiflix.webtestapp.controller;

import br.com.isiflix.webtestapp.model.Produto;
import br.com.isiflix.webtestapp.service.Iservice;
import io.isiflix.isispring.annotations.IsiBody;
import io.isiflix.isispring.annotations.IsiController;
import io.isiflix.isispring.annotations.IsiPostMethod;
import io.isiflix.isispring.annotations.IsiGetMethod;
import io.isiflix.isispring.annotations.IsiInjected;

@IsiController
public class HelloController {
	
	
	@IsiInjected Iservice service;
	
	
	@IsiGetMethod("/hello")
	public String sayHelloWord() {
		return "Hello Word";
	}
	@IsiGetMethod("/teste")
	public String sayTeste() {
		return ("teste funcionando");
	}
	@IsiGetMethod("/produto")
	public Produto exibirProduto() {
		Produto p = new Produto();
		p.setId(123456);
		p.setLinkFoto("Computador.jpg");
		p.setNome("Computador");
		p.setPreco(1200.0);
		return p;
	}
	
	@IsiPostMethod("/produto")
	public Produto cadastrarProduto(@IsiBody Produto novo) {
		System.out.println(novo);
		return novo;
	}
	@IsiGetMethod("/injected")
	public String sayCustomMessage() {
		return service.sayCustomMessage("hello word");
	}
	
	

}
