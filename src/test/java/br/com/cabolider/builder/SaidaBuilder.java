package br.com.cabolider.builder;

import java.util.Calendar;

import br.com.cabolider.modelo.Saida;

public class SaidaBuilder {

	private Saida saida=new Saida();

	public SaidaBuilder codigo(String codigo) {
		saida.setCodigo(codigo);
		return this;
	}

	public SaidaBuilder descricao(String descricao) {
		saida.setDescricao(descricao);
		return this;
	}

	public SaidaBuilder tamanho(String tamanho) {
		saida.setTamanho(tamanho);
		return this;
	}

	public SaidaBuilder nomeDoCliente(String cliente) {
		saida.setNomeDoCliente(cliente);
		return this;
	}

	public SaidaBuilder qtdeRetirada(int qtdeRetirada) {
		saida.setQuantidadeASerRetirada(qtdeRetirada);
		return this;
	}

	public SaidaBuilder data(Calendar data) {
		saida.setData(data);
		return this;
	}
	
	public Saida constroi(){
		return saida;
	}

}
