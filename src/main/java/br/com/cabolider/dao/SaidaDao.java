package br.com.cabolider.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.cabolider.modelo.Saida;
import br.com.cabolider.util.JPAUtil;

public class SaidaDao implements Serializable {

	private static final long serialVersionUID = 1L;

	public void gravarSaida(Saida produtoSaida) {
		EntityManager manager = new JPAUtil().getEntityManager();
		produtoSaida.setId(null);
		manager.getTransaction().begin();
		manager.persist(produtoSaida);
		manager.getTransaction().commit();
		manager.close();
	}

	public List<Saida> listaItensDeSaida() {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Saida> query = manager.createQuery(
				"select s from Saida s order by s.data DESC", Saida.class);
		List<Saida> produtos = query.getResultList();
		manager.close();
		return produtos;
	}
}
