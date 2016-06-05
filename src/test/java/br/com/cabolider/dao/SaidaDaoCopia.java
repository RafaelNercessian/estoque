package br.com.cabolider.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.cabolider.modelo.Saida;

//Foram retirados os: manager.getTransaction.begin(), manager.getTransaction.commit() 
//e manager.close() de todos os métodos para que fosse possível realizar o teste

public class SaidaDaoCopia implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;

	public SaidaDaoCopia(EntityManager manager){
		this.manager=manager;
	}
		
	public void gravarSaida(Saida produtoSaida) {
		manager.persist(produtoSaida);
	}

	public List<Saida> listaItensDeSaida() {
		TypedQuery<Saida> query = manager.createQuery(
				"select s from Saida s order by s.data DESC", Saida.class);
		List<Saida> produtos = query.getResultList();
		return produtos;
	}
}
