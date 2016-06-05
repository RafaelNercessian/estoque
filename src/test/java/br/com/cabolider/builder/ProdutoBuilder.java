package br.com.cabolider.builder;

import br.com.cabolider.modelo.Produto;

public class ProdutoBuilder {

	private Produto produto = new Produto();

	public ProdutoBuilder codigo(String codigo){
		produto.setCodigo(codigo);
		return this;
	}
	
	public ProdutoBuilder descricao(String descricao){
		produto.setDescricao(descricao);
		return this;
	}
	
	public ProdutoBuilder localizacao(String localizacao){
		produto.setLocalizacao(localizacao);
		return this;
	}
	
	public ProdutoBuilder cor(String cor){
		produto.setCor(cor);
		return this;
	}
	
	
	public ProdutoBuilder tipoDeCobre(String tipoDeCobre){
		produto.setTipoDeCobre(tipoDeCobre);
		return this;
	}
	
	public ProdutoBuilder tamanho(String tamanho){
		produto.setTamanho(tamanho);
		return this;
	}
	
	public ProdutoBuilder saldo(Integer saldo){
		produto.setSaldo(saldo);
		return this;
	}
	
	public Produto constroi(){
		return produto;
	}
	
}
