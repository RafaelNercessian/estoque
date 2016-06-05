package br.com.cabolider.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.cabolider.modelo.Entrada;
import br.com.cabolider.util.JPAUtil;

public class EntradaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	public void gravarEntrada(Entrada produtoEntrada) {
		EntityManager manager = new JPAUtil().getEntityManager();
		produtoEntrada.setId(null);
		manager.getTransaction().begin();
		manager.persist(produtoEntrada);
		manager.getTransaction().commit();
		manager.close();
	}

	public List<Entrada> listarProdutosInseridos() {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Entrada> listaProdutosInseridos = manager.createQuery(
				"select e from Entrada e order by e.data DESC", Entrada.class);
		List<Entrada> retornoListaProdutos = listaProdutosInseridos
				.getResultList();
		manager.close();
		return retornoListaProdutos;
	}
}
