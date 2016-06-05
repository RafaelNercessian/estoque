package br.com.cabolider.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.cabolider.modelo.Produto;

//Foram retirados os: manager.getTransaction.begin(), manager.getTransaction.commit() 
//e manager.close() de todos os métodos para que fosse possível realizar o teste

public class ProdutoDaoCopia implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager manager;

	public ProdutoDaoCopia(EntityManager manager) {
		this.manager = manager;
	}

	public void gravar(Produto produto) {
		manager.persist(produto);
	}

	public List<Produto> listaPorProduto(Produto produto) {
		TypedQuery<Produto> query = manager
				.createQuery(
						"select p from Produto p where p.codigo like :codigo or p.descricao like :descricao order by p.codigo",
						Produto.class)
				.setParameter("codigo", produto.getCodigo() + "%")
				.setParameter("descricao", "%" + produto.getDescricao() + "%");
		List<Produto> listaRetornada = query.getResultList();
		return listaRetornada;
	}

	public Produto retornaProduto(Produto produto) {
		try {
			TypedQuery<Produto> query = manager
					.createQuery(
							"select p from Produto p where p.codigo=:codigo and p.tamanho=:tamanho",
							Produto.class)
					.setParameter("codigo", produto.getCodigo())
					.setParameter("tamanho", produto.getTamanho());
			Produto produtoRetornado = query.getSingleResult();
			return produtoRetornado;
		} catch (RuntimeException e) {
			return null;
		}

	}

	public void altera(Produto produto) {
		manager.merge(produto);
	}
	
	public List<Produto> pesquisaProdutoPeloCodigo(Produto produto) {
		TypedQuery<Produto> query = manager
				.createQuery("select p from Produto p where p.codigo=:codigo",
						Produto.class);
		query.setParameter("codigo", produto.getCodigo());
		List<Produto> pesquisaProdutoPeloCodigo = query.getResultList();
		if (pesquisaProdutoPeloCodigo.isEmpty())
			return null;
		else
			return pesquisaProdutoPeloCodigo;
	}

}
