package br.com.cabolider.builder;

import java.util.Calendar;

import br.com.cabolider.modelo.Entrada;

public class EntradaBuilder {

	private Entrada entrada = new Entrada();
	
	public EntradaBuilder codigo(String codigo) {
		entrada.setCodigo(codigo);
		return this;
	}

	public EntradaBuilder descricao(String descricao) {
		entrada.setDescricao(descricao);
		return this;
	}

	public EntradaBuilder ordemDeProducao(String ordem) {
		entrada.setOrdemDeProducao(ordem);
		return this;
	}

	public EntradaBuilder qtdeDeEntrada(int quantidade) {
		entrada.setQuantidadeEntrada(quantidade);
		return this;
	}

	public EntradaBuilder tamanho(String tamanho) {
		entrada.setTamanho(tamanho);
		return this;
	}

	public EntradaBuilder data(Calendar data) {
		entrada.setData(data);
		return this;
	}

	public Entrada constroi() {
		return entrada;
		
	}

}
