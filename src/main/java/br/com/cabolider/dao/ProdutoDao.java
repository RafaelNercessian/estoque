package br.com.cabolider.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.cabolider.modelo.Produto;
import br.com.cabolider.util.JPAUtil;

public class ProdutoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	public void gravar(Produto produto) {
		EntityManager manager = new JPAUtil().getEntityManager();
		produto.setId(null);
		manager.getTransaction().begin();
		manager.persist(produto);
		manager.getTransaction().commit();
		manager.close();
	}

	public List<Produto> listaPorProduto(Produto produto) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Produto> query = manager
				.createQuery(
						"select p from Produto p where p.codigo like :codigo or p.descricao like :descricao order by p.codigo",
						Produto.class)
				.setParameter("codigo", produto.getCodigo() + "%")
				.setParameter("descricao", "%" + produto.getDescricao() + "%");
		List<Produto> listaRetornada = query.getResultList();
		manager.close();
		return listaRetornada;
	}

	public Produto retornaProduto(Produto produto) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		try {
			TypedQuery<Produto> query = manager
					.createQuery(
							"select p from Produto p where p.codigo=:codigo and p.tamanho=:tamanho",
							Produto.class)
					.setParameter("codigo", produto.getCodigo())
					.setParameter("tamanho", produto.getTamanho());
			Produto produtoRetornado = query.getSingleResult();
			manager.close();
			return produtoRetornado;
		} catch (RuntimeException e) {
			manager.close();
			return null;
		}

	}

	public void altera(Produto produto) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.merge(produto);
		manager.getTransaction().commit();
		manager.close();
	}

	public List<Produto> pesquisaProdutoPeloCodigo(Produto produto) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Produto> query = manager
				.createQuery("select p from Produto p where p.codigo=:codigo",
						Produto.class);
		query.setParameter("codigo", produto.getCodigo());
		List<Produto> pesquisaProdutoPeloCodigo = query.getResultList();
		manager.close();
		if (pesquisaProdutoPeloCodigo.isEmpty())
			return null;
		else
			return pesquisaProdutoPeloCodigo;
	}

	public void remove(Produto produto){
		EntityManager manager = new JPAUtil().getEntityManager();
		Produto retorno = manager.find(Produto.class, produto.getId());
		manager.getTransaction().begin();
		manager.remove(retorno);
		manager.getTransaction().commit();
		manager.close();
	}
}
