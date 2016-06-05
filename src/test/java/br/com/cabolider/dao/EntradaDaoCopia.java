package br.com.cabolider.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.cabolider.modelo.Entrada;

//Foram retirados os: manager.getTransaction.begin(), manager.getTransaction.commit() 
//e manager.close() de todos os métodos para que fosse possível realizar o teste

public class EntradaDaoCopia implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;

	public EntradaDaoCopia(EntityManager manager) {
		this.manager = manager;
	}

	public void gravarEntrada(Entrada produtoEntrada) {
		manager.persist(produtoEntrada);
	}

	public List<Entrada> listarProdutosInseridos() {
		TypedQuery<Entrada> listaProdutosInseridos = manager.createQuery(
				"select e from Entrada e order by e.data DESC", Entrada.class);
		List<Entrada> retornoListaProdutos = listaProdutosInseridos
				.getResultList();
		return retornoListaProdutos;
	}
}
